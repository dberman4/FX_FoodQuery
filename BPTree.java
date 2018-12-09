package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        this.root = new LeafNode();
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        this.root.insert(key, value);
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        if (key == null) {
            return new ArrayList<V>();
        }
        
        return root.rangeSearch(key, comparator);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            this.keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            this.children = new ArrayList<Node>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return children.size() > branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            //finds the location the key should be inserted at
            int searchLocation = Collections.binarySearch(keys, key);
            int childLocation;
            //checks to see if the key already exists in the list or not
            if (searchLocation >= 0) {
                childLocation = searchLocation + 1;
            }
            else {
                childLocation = -searchLocation -1;
            }
            //creates a new node and inserts the child into the list
            Node child = children.get(childLocation);
            child.insert(key, value);
            
            //check to see if the child is now overflowed
            if (child.isOverflow()) {
                Node newSibling = child.split();
                //insert the new sibling
                searchLocation = Collections.binarySearch(keys, newSibling.getFirstLeafKey());
                //checks to see if the key already exists in the list or not
                if (searchLocation >= 0) {
                    childLocation = searchLocation + 1;
                }
                else {
                    childLocation = -searchLocation -1;
                }
                //add the new sibling to the children and the first leaf of that to the keys
                this.keys.add(childLocation, newSibling.getFirstLeafKey());
                this.children.add(childLocation + 1, newSibling);
            }
            
            //check to see if the root is now overflowed as result
            if (root.isOverflow()) {
                //create newly split sibling
                Node newSibling = split();
                //create the new root
                InternalNode newRoot = new InternalNode();
                //get the first leaf key of newly created sibling
                newRoot.keys.add(newSibling.getFirstLeafKey());
                //insert the children into the new root
                newRoot.children.add(this);
                newRoot.children.add(newSibling);
                //update to the new root
                root = newRoot;
                
            }
            
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            //find the split point
            int start = keys.size() / 2 + 1;
            int end = keys.size();
            InternalNode newSibling = new InternalNode();
            //add all the keys and children to the new sibling
            newSibling.keys.addAll(this.keys.subList(start, end));
            newSibling.children.addAll(this.children.subList(start, end + 1));
            //clear the keys and children from the current lists
            this.keys.subList(start - 1, end).clear();
            this.children.subList(start, end + 1).clear();
            return newSibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            int childLocation = Collections.binarySearch(keys, key);
            //finds the start location for the search
            if (childLocation >= 0) {
                childLocation = childLocation + 1;
            }
            else {
                childLocation = -childLocation -1;
            }
            //return the range search for the given key and comparator
            return this.children.get(childLocation).rangeSearch(key, comparator);
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            this.values = new ArrayList<V>();
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return this.values.size() > branchingFactor - 1;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            int findLocation = Collections.binarySearch(keys, key);
            int insertLocation;
            //assign the correct insert location given the found location
            if (findLocation >= 0) {
                insertLocation = findLocation;
            }
            else {
                insertLocation = -findLocation -1;
            }
            //insert in the new value and key
            this.values.add(insertLocation, value);
            this.keys.add(insertLocation, key);
            
            //check to see if the root is overflowed
            if (root.isOverflow()) {
                Node newSibling = split();
                InternalNode newRoot = new InternalNode();
                //get the first leaf key of newly created sibling
                newRoot.keys.add(newSibling.getFirstLeafKey());
                //add children to the newly created root
                newRoot.children.add(this);
                newRoot.children.add(newSibling);
                //update to new root
                root = newRoot;
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            LeafNode newSibling = new LeafNode();
            int start = (keys.size() + 1) /2;
            int end = keys.size();
            //add all the keys and values to the new sibling
            newSibling.keys.addAll(keys.subList(start, end));
            newSibling.values.addAll(values.subList(start, end));
            //clear the old keys and values lists
            keys.subList(start, end).clear();
            values.subList(start, end).clear();
            //update the next sibling in the list after the split
            newSibling.next = this.next;
            this.next = newSibling;
            newSibling.previous = this;
            return newSibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            //create list to be returned
            List<V> returnList = new ArrayList<V>();
            //get the current leafnode
            LeafNode current = this;
            
            //idk if this is needed
            //int findLocation = Collections.binarySearch(keys, key);
            //int startLocation;
            //assign the correct insert location given the found location
            //if (findLocation >= 0) {
            //    startLocation = findLocation;
            //}
            //else {
            //    startLocation = -findLocation -1;
            //}
            
            while (current != null) {
                //make iterator for current leafnode value and key lists
                Iterator<K> keyIterator = current.keys.iterator();
                Iterator<V> valueIterator = current.values.iterator();
                //iterate over lists to find acceptable values
                while (keyIterator.hasNext()) {
                    //get the next value and key
                    K currentKey = keyIterator.next();
                    V currentValue = valueIterator.next();
                    //compare the current key to the key to be compared
                    int compareValue = currentKey.compareTo(key);
                    //compare the result to the comparator and add to return list if correct
                    if (comparator.equals("==") && compareValue == 0) {
                        returnList.add(currentValue);
                    }
                    else if (comparator.equals(">=") && compareValue >= 0) {
                        returnList.add(currentValue);
                    }
                    else if (comparator.equals("<=") && compareValue <= 0) {
                        returnList.add(currentValue);
                    }
                }
                //get the next leafnode
                current = current.next;
            }
            //return the finished list
            return returnList;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
