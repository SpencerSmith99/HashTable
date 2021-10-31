package dictionary;


import java.util.*;
import java.io.*;
import java.util.Dictionary;

/**
 *
 * @author Spencer Smith
 * @author Anna Fortenberry
 */

class HashNode<K,V> {
    K key;
    V value;
    final int hashCode;

    HashNode<K,V> next;    // in case of chaining, the next value of the current element

    public HashNode(K key, V value, int hashCode) {
        this.key = key;
        this.value = value;
        this.hashCode = hashCode;
    }
}

public class MyHashTable<K,V> extends Dictionary<K,V> implements Cloneable, Serializable {

    private int tableSize;
    private double loadFactor;
    private int addedElements;
    private Vector<HashNode<K,V>> bucket;

    public MyHashTable() {
        this.tableSize = 11;             // initial table size
        this.loadFactor = 0.75;       // load factor of the hash table
        this.addedElements = 0;          // keeps track of added elements
        bucket = new Vector<HashNode<K,V>>(tableSize);    // initialize the vector

        for(int i = 0; i < tableSize; i++) {
            bucket.add(null);   //create empty chains
        }
    }

    ///////////////////////// UTILITY FUNCTIONS ///////////////////////////

    private final int hashCode(Object key) {
        return Objects.hashCode(key);
    }

    private int getBucketIndex(Object key) {
        int hashCode = hashCode(key);
        int index = hashCode % tableSize;
        // since hashCode could be negative, check for it
        index = index < 0 ? index * -1 : index;

        return index;
    }

    public void reHash() {
        tableSize = tableSize * 2;  //double the table size
        Vector<HashNode<K,V>> temp = bucket;
        bucket = new Vector<>();
        addedElements = 0;
        for(int i = 0; i < tableSize; i++)
            bucket.add(null);

        for(HashNode<K,V> headNode : temp) {
            while(headNode != null) {
                put(headNode.key, headNode.value);
                headNode = headNode.next;
            }
        }
    }

    //////////////////// FUNCTIONS FOR HASH TABLE ///////////////////////////////////

    public int generateKey(String word) { return word.hashCode(); }

    public int size() {
        return this.addedElements;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Enumeration<K> keys() {
        return null;
    }

    @Override
    public Enumeration<V> elements() {
        return null;
    }

    public boolean containsKey(Object key) {
        int bucketIndex = getBucketIndex(key);
        return bucket.get(bucketIndex) != null;
    }

    public V get(Object key) {
        //get the index of the bucket, given the key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K,V> head = bucket.get(bucketIndex);   //get the head of the list

        while(head != null) {
            if(head.key.equals(key) && head.hashCode == hashCode)
                return head.value;  //return the value corresponding to the key

            head = head.next;   //get the next value in the list
        }

        //key was not found, return null
        return null;
    }


    public V put(K key, V value) {
        //get the bucket index, given the key
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);
        HashNode<K,V> head = bucket.get(bucketIndex);   //get the head of the list

        //check if the key is already present in the list
        while(head != null) {
            if(head.key.equals(key) && head.hashCode == hashCode) {
                V replacedValue = head.value;   //keep track of the replaced value
                head.value = value;     //update the node
                return replacedValue;   //return replaced value
            }
            head = head.next;
        }

        // insert the key in chain
        addedElements++;    //increment the num of elements in the table
        head = bucket.get(bucketIndex);     //get the head
        HashNode<K,V> newNode = new HashNode<K,V>(key, value, hashCode);
        newNode.next = head;    //store the new node at top of bucket
        bucket.set(bucketIndex, newNode);

        //check to see if load factor goes beyond the threshold
        // if it does, call rehash
        if((1.0 * addedElements) / tableSize >= loadFactor) {
            reHash();
        }
        //by default, return null
        return null;
    }

    public V remove(Object key) {
        int bucketIndex = getBucketIndex(key);
        int hashCode = hashCode(key);

        HashNode<K,V> head = bucket.get(bucketIndex);   //store the head of the linked list
        HashNode<K,V> prev = null;      //initialize new HashNode to keep track of linked list

        while(head != null) {
            if(head.key.equals(key) && hashCode == head.hashCode)
                break;

            prev = head;        //keep track of head, store in previous
            head = head.next;   //new head is equal to the next element in the chain
        }

        if(head == null)
            return null;    //if key was not found, return null

        tableSize--;        //reduce size of table

        if(prev != null)    //if there is more than two element in the bucketIndex
            prev.next = head.next;  //fill the gap between the nodes
        else        //if there are just two elements in bucketIndex
            bucket.set(bucketIndex, head.next);

        return head.value;
    }

    public void clear() {
        bucket.setSize(11);
        for(int i = 0; i < 11; i++) {
            bucket.add(null);
        }
    }

    public Iterable<K> keySet() {

        return null;
    }

    public Iterable<V> values() {
        return null;
    }
}



