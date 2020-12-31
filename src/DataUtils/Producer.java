package DataUtils;

import DataUtils.Listeners.BufferFilledListener;

public class Producer implements Runnable {
	QueueItem mItem;
	Buffer mBuffer;
	BufferFilledListener mListener;
	public Producer(Buffer buffer, BufferFilledListener listener) {
		mBuffer = buffer;
		mListener = listener;
	}

	public void setItem(QueueItem item) {
		mItem = item;
	}

	@Override
	public void run() {
		mBuffer.insert(mItem);
		if(mBuffer.getSize() == mBuffer.getmBufferMaxSize()) {
			mListener.onBufferFilled();
		}
	}
}