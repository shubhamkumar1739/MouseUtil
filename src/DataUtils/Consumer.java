package DataUtils;

import DataUtils.Listeners.ConsumedListener;

import java.util.*;
public class Consumer implements Runnable{
	Buffer mBuffer;
	ArrayList<QueueItem> mItems;
	ConsumedListener mListener;
	public Consumer(Buffer buffer, ConsumedListener listener) {
		mBuffer = buffer;
		mListener = listener;
	}

	@Override
	public void run() {
		mItems = mBuffer.getBuffer();
		if(mItems != null) {
			mListener.onConsumed(mItems);
		}
	}
}