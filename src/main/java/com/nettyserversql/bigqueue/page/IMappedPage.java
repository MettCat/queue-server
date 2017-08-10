package com.nettyserversql.bigqueue.page;

import java.nio.ByteBuffer;

/**
 * 内存映射页面文件ADT
 * Memory mapped page file ADT
 * 
 * @author bulldog
 *
 */
public interface IMappedPage {
	
	/**
	 *：获取映射页面缓冲区的线程本地副本
	 * Get a thread local copy of the mapped page buffer
	 * 
	 * @param position start position(relative to the start position of source mapped page buffer) of the thread local buffer
	 * @return a byte buffer with specific position as start position.
	 */
	ByteBuffer getLocal(int position);
	
	/**
	 * Get data from a thread local copy of the mapped page buffer
	 * 
	 * @param position start position(relative to the start position of source mapped page buffer) of the thread local buffer
	 * @param length the length to fetch
	 * @return byte data
	 */
	public byte[] getLocal(int position, int length);
	
	/**
	 * Check if this mapped page has been closed or not
	 * 
	 * @return
	 */
	boolean isClosed();
	
	/**
	 * Set if the mapped page has been changed or not
	 * 
	 * @param dirty
	 */
	void setDirty(boolean dirty);
	
	/**
	 * The back page file name of the mapped page
	 * 
	 * @return
	 */
	String getPageFile();
	
	/**
	 * The index of the mapped page
	 * 
	 * @return the index
	 */
	long getPageIndex();
	
	/**
	 * Persist any changes to disk
	 */
	public void flush();
}
