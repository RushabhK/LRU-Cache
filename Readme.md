# Disk backed LRU Cache

## Overview
This project is a java implementation of LRU cache with the following features:
 - Allows get and put requests on the LRU cache
 - Allows the cache to dump into a file on disk
 - Allows to prime the cache with its last state from the dumped file. 

The LRU cache provides the following api's:
 - V get(K key)
 - void put(K key, V value)


## Steps to execute
#### 1. Clone project
```
$ git clone https://github.com/RushabhK/LRU-Cache.git
$ cd LRU-Cache
```

#### 2. To run the tests
Compile the java files in the src folder
```
$ javac -d bin -cp lib/testng.jar src/*.java -Xlint:unchecked
```
Run the testng.xml file
```
$ java -cp lib/testng.jar:lib/jcommander-1.27.jar:./bin org.testng.TestNG testng.xml
```

This will run all the tests and will create a test-output directory where the results of the tests can be accessed from the index.html file. 

## How to use

#### 1. Create a LRUCache<K, V> object specifying its size limit
- Source: LRUCache class
```sh
LRUCache<Integer, String> obj = new LRUCache<Integer, String>(50); // Max size of cache is 50
```

#### 2. To insert key, value into the LRUCache object
 - API: put(key, value)
 - Source: LRUCache class
 - Functionality: Inserts key, value as the most recently used entry in the LRUCache
```
obj.put(1, "First element");
```

#### 3. To fetch value from the LRUCache object
 - API: get(key)
 - Source: LRUCache class
 - Functionality: Returns value if key is present, else returns null
```
String value = obj.get(1);
```

#### 4. To dump the cache state into a file on disk
 - API: dumpInto(LRUCache obj, String filename)
 - Source: Disk class
 - Functionality: Complete dump the LRUCache obj into the file on the disk
 ```
 Disk.dumpInto(obj, "recoveryFile");
 ```

#### 5. Recover the LRUCache object from the file on disk
 - API: recoverCache(String filename)
 - Source: Disk class
 - Functionality: Recovers and returns the LRUCache object with its dumped state from the file on the disk
 ```
 LRUCache obj<Integer, String> = Disk.recoverCache("recoveryFile");
 ```


## Assumptions
  - I have considered some max size of a LRUCache object, which is initialized while creating the object in the LRUCache constructor as a parameter.
  - If the current size of the LRUCache object is equal to its max size and a new entry needs to be added, remove the least recently used entry and insert the new entry in the cache.


## Algorithm, Data structures used and Implementation details
Two data structures used to implement the LRUCache
 - Queue : Implemented using a doubly linked list. This is the DoublyLL class in the src folder. The queue maintains two nodes of least recently used and most recently used Nodes.(The structure of the Node is specified in the Node class in the src folder). These nodes are updated on get and put request. On every get and put request, the node object corresponding to the key is moved to the most recently used node. The least recently used node is removed when the cache size is maximum and a put request of new entry is made.
 - HashMap : This maintains the mapping of the key vs the Node object corresponding to the key in the queue. This allows the referencing of the nodes on the queue in O(1) time complexity. Thus helps in optimizing the delete operation in the queue in O(1) time complexity. On every put request, the HashMap is updated. The key corresponding to the least recently used node is removed from the HashMap when the cache size is maximum and a put request of new entry is made. All operations are done in O(1) time complexity.

Disk class methods to dump and recover the LRUCache objects:
 - dumpInto(LRUCache obj, String filename): Used the java serialization to write the LRUCache object to the filename specified.
 - recoverCache(String filename): Used the java deserialization to prime the LRUCache object with its last state from the file on disk.

To ensure the get and put api's to be thread safe, the get and put methods are made synchronized. This will ensure only one method of an LRUCache object to be called at a time. This helps the api's to be used in a multithreaded setup.

## Time complexity
 - V get(K) : O(1)
 - void put(K, V) : O(1)
 
 ## Space Complexity
Considering N as the maximum size of the LRUCache object, K as the maximum possible length of the key and V as the maximum possible length of the value: 
 - Space complexity for Doubly Linked list = O(N*(K+V))
 - Space complexity for HashMap = O(N*(K+1)) = O(N*K)
 - Thus, overall space complexity of LRUCache = O(N*(K+V))
 
 ## Testing
 - Used TestNG framework to test the api's in multithreaded pool and also for unit testing.