package DataUtils;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	private ReentrantLock mutex = new ReentrantLock();
	Queue<QueueItem> mBufferQueue;
	int mBufferMaxSize;
	public Buffer(int size) {
		mBufferMaxSize = size;
		mBufferQueue = new PriorityQueue<>();
	}

	public void insert(QueueItem item) {
		mutex.lock();
		if(mBufferQueue.size() == mBufferMaxSize) {
			mBufferQueue.poll();
		}
		mBufferQueue.add(item);
		mutex.unlock();
	}

	public ArrayList<QueueItem> getBuffer() {
		mutex.lock();

		if(mBufferQueue.size() == 0) {
			return null;
		}
		ArrayList<QueueItem> items = new ArrayList<>(mBufferQueue);
		Collections.sort(items);
		mBufferQueue = new PriorityQueue<QueueItem>();

		mutex.unlock();

		return items;
	}

	public int getSize() {
		return mBufferQueue.size();
	}

	public int getmBufferMaxSize() {
		return mBufferMaxSize;
	}
}