package pwr.bts;

import java.io.File;
import java.io.IOException;

import pwr.bts.processor.BitStreamProcessorContainer;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.FileBitInputStream;

public class Simulator {
	
	private BitInputStream sent, transOut, chanOut, received;
	private BitStreamProcessorContainer transmitter, channel, receiver;
	private double ber, dataSizeEfficiency;
	private long desyncsCount;
	
	public void readFile(File file) throws IOException {
		sent = new FileBitInputStream(file);
	}
	
	public void readStream(BitInputStream stream) throws IOException {
		sent = stream;
	}
	
	public void simulate() {
		sent.reset();
		transOut = transmitter.process(sent);
		chanOut = channel.process(transOut);
		received = receiver.process(chanOut);
	}
	
	public void simulate(int desyncBitCount) {
		desyncsCount = 0;
		sent.reset();
		
		transOut = transmitter.process(sent);
		
		long initialDataLength = sent.getPointer();
		
		chanOut = channel.process(transOut);
		
		long lastBit = 0;
		int consecutiveSameBits = 0;
		
		while (chanOut.hasNext()) {
			if (lastBit != chanOut.peek())
				consecutiveSameBits = 1;
			else
				consecutiveSameBits++;
			
			lastBit = chanOut.next();
			
			if (consecutiveSameBits >= desyncBitCount)
				desyncsCount++;
		}
		
		chanOut.reset();
		
		received = receiver.process(chanOut);
		
		long sentDataLength = chanOut.getPointer();
		
		sent.reset();
		
		long allBits = 0, errorBits = 0;
		
		while (sent.hasNext()) {			
			if (sent.next() != received.next())
				errorBits++;
			
			allBits++;
		}
		
		ber = (double) errorBits / allBits;
		dataSizeEfficiency = (double) sentDataLength / initialDataLength;
	}
	
	public double getBitErrorRate() {
		return ber;
	}
	
	public long getDesyncsCount() {
		return desyncsCount;
	}
	
	public double getDataSizeEfficiency() {
		return dataSizeEfficiency;
	}

	public void setTransmitter(BitStreamProcessorContainer transmitter) {
		this.transmitter = transmitter;
	}

	public void setChannel(BitStreamProcessorContainer channel) {
		this.channel = channel;
	}

	public void setReceiver(BitStreamProcessorContainer receiver) {
		this.receiver = receiver;
	}
	
	public BitInputStream getSent() {
		sent.reset();
		return sent;
	}

	public BitInputStream getTransOut() {
		transOut.reset();
		return transOut;
	}

	public BitInputStream getChanOut() {
		chanOut.reset();
		return chanOut;
	}

	public BitInputStream getReceived() {
		received.reset();
		return received;
	}
	
	public void reset() {
		sent = null;
		ber = dataSizeEfficiency = desyncsCount = 0;
	}
}
