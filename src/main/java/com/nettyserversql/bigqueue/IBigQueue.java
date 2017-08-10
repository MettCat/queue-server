package com.nettyserversql.bigqueue;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.Closeable;
import java.io.IOException;

/**
 * Queue ADT
 * 
 * @author bulldog
 *
 */
public interface IBigQueue extends Closeable {

	/**
	 * Determines whether a queue is empty：确定队列是否为空
	 * 
	 * @return ture if empty, false otherwise
	 */
	public boolean isEmpty();
	
	/**
	 * Adds an item at the back of a queue
	 * 在队列的后面添加一个项目
	 * 
	 * @param data to be enqueued data
	 * @throws IOException exception throws if there is any IO error during enqueue operation.
	 */
	public void enqueue(byte[] data)  throws IOException;
	
	/**
	 * Retrieves and removes the front of a queue  检索和删除队列的前端
	 * 
	 * @return data at the front of a queue
	 * @throws IOException exception throws if there is any IO error during dequeue operation.
	 */
	public byte[] dequeue() throws IOException;

    /**
     * Retrieves a Future which will complete if new Items where enqued. 检索未来，如果新项目被填入，将会完成。
     *
     * Use this method to retrieve a future where to register as Listener instead of repeatedly polling the queues state.
	 * 使用该方法来检索将来作为侦听器注册的未来，而不是重复轮询队列状态。
     * On complete this future contains the result of the dequeue operation. Hence the item was automatically removed from the queue.
     * 完整的这个未来包含了dequeue操作的结果。因此，该项被自动从队列中删除。
     * @return a ListenableFuture which completes with the first entry if items are ready to be dequeued.
     */
    public ListenableFuture<byte[]> dequeueAsync();

	
	/**
	 * Removes all items of a queue, this will empty the queue and delete all back data files.
	 * 删除队列的所有项，这将清空队列，并删除所有的后数据文件。
	 * @throws IOException exception throws if there is any IO error during dequeue operation.
	 */
	public void removeAll() throws IOException;
	
	/**
	 * Retrieves the item at the front of a queue
	 * 
	 * @return data at the front of a queue
	 * @throws IOException exception throws if there is any IO error during peek operation.
	 */
	public byte[] peek()  throws IOException;


    /**
     * Retrieves the item at the front of a queue asynchronously.
     * On complete the value set in this future is the result of the peek operation. Hence the item remains at the front of the list.
     *
     * @return a future containing the first item if available. You may register as listener at this future to be informed if a new item arrives.
     */
    public ListenableFuture<byte[]> peekAsync();

    /**
     * apply an implementation of a ItemIterator interface for each queue item
     *：为每个队列项应用一个ItemIterator接口的实现
     * @param iterator
     * @throws IOException
     */
    public void applyForEach(ItemIterator iterator) throws IOException;
	
	/**
	 * Delete all used data files to free disk space.
	 * 
	 * BigQueue will persist enqueued data in disk files, these data files will remain even after
	 * the data in them has been dequeued later, so your application is responsible to periodically call
	 * this method to delete all used data files and free disk space.
	 * 
	 * @throws IOException exception throws if there is any IO error during gc operation.
	 */
	public void gc() throws IOException;
	
	/**
	 * Force to persist current state of the queue, 
	 * 强制保持队列的当前状态，
	 * normally, you don't need to flush explicitly since:通常情况下，您不需要显式地刷新:
	 * 1.) BigQueue will automatically flush a cached page when it is replaced out,BigQueue将自动刷新缓存页面，当它被替换时，
	 * 2.) BigQueue uses memory mapped file technology internally, and the OS will flush the changes even your process crashes,
	 * 在内部使用内存映射文件技术，操作系统将刷新更改，甚至您的进程崩溃
	 * call this periodically only if you need transactional reliability and you are aware of the cost to performance.
	 * 只有在需要事务可靠性的情况下才会定期调用此功能，并且您知道性能的成本。
	 */
	public void flush();
	
	/**：队列中可用的项目总数。
	 * Total number of items available in the queue.
	 * @return total number
	 */
	public long size();
	
	/**
	 * Item iterator interface
	 */
	public static interface ItemIterator {
        /**
         * Method to be executed for each queue item
         *
         * @param item queue item
         * @throws IOException
         */
        public void forEach(byte[] item) throws IOException;
    }
}
