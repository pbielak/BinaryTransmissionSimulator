package pwr.bts;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import pwr.bts.processor.BitErrorGenerator;
import pwr.bts.processor.BitStreamProcessorContainer;
import pwr.bts.processor.MultiplicativeDescrambler;
import pwr.bts.processor.MultiplicativeScrambler;
import pwr.bts.processor.SegmentErrorGenerator;
import pwr.bts.processor.SimpleAdditiveScrambler;
import pwr.bts.processor.TMDSDecoder;
import pwr.bts.processor.TMDSEncoder;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.FileBitInputStream;
import pwr.bts.stream.RandomBitInputStream;

public class ComplexResultsModule {
	
	String[] methodNames = {"Additive Scrambler", "Multiplicative Scrambler", "TMDS"};
	String[] fileNames = {"Obraz", "Dzwiek", "Szum"};
	String[] errorTypes = {"Bledy na pojedynczych bitach", "Bledy segmentowe o ustalonej sredniej dlugosci", "Bledy segmentowe o zmiennej sredniej dlugosci"};
	String[] errorTypeColumns = {"Procent bledow", "Procent bledow", "Srednia dlugosc"};
	double[] xValues = {100.0 / 128, 100.0 / 64, 100.0 / 32, 100.0 / 16, 100.0 / 128, 100.0 / 64, 100.0 / 32, 100.0 / 16, 4, 8, 16, 32};
	
	BitStreamProcessorContainer[] transmitters = {
			new BitStreamProcessorContainer(new SimpleAdditiveScrambler(-7008052270312348889L, 2)),
			new BitStreamProcessorContainer(new MultiplicativeScrambler(-7008052270312348889L, 2)),
			new BitStreamProcessorContainer(new TMDSEncoder())
	};
	
	BitStreamProcessorContainer[] channels = {
			new BitStreamProcessorContainer(new BitErrorGenerator(128, 16)),
			new BitStreamProcessorContainer(new BitErrorGenerator(64, 8)),
			new BitStreamProcessorContainer(new BitErrorGenerator(32, 4)),
			new BitStreamProcessorContainer(new BitErrorGenerator(16, 2)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(512, 64, 4, 1)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(256, 32, 4, 1)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(128, 16, 4, 1)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(64, 8, 4, 1)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(64, 8, 4, 1)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(128, 16, 8, 2)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(256, 32, 16, 4)),
			new BitStreamProcessorContainer(new SegmentErrorGenerator(512, 64, 32, 8))
	};
	
	BitStreamProcessorContainer[] receivers = {
			new BitStreamProcessorContainer(new SimpleAdditiveScrambler(-7008052270312348889L, 2)),
			new BitStreamProcessorContainer(new MultiplicativeDescrambler(-7008052270312348889L, 2)),
			new BitStreamProcessorContainer(new TMDSDecoder())
	};
	
	BitInputStream[] streams = new BitInputStream[3];
	
	private final Simulator simulator = new Simulator();
	
	double[][][] bers = new double[streams.length][transmitters.length][channels.length];
	long[][][] desyncs = new long[streams.length][transmitters.length][channels.length];
	
	public ComplexResultsModule() throws IOException {
		streams[0] = new FileBitInputStream(new File("pliktest.png"));
		streams[1] = new FileBitInputStream(new File("pliktest.wav"));
		streams[2] = new RandomBitInputStream(1024 * 1024);
	}
	
	public void generateResults(File outputFile) throws IOException {
		PrintStream out = new PrintStream(outputFile);
		
		for (int stream = 0; stream < streams.length; stream++) {
			simulator.readStream(streams[stream]);
			
			for (int method = 0; method < transmitters.length; method++) {
				simulator.setTransmitter(transmitters[method]);
				simulator.setReceiver(receivers[method]);
				
				for (int error = 0; error < channels.length; error++) {
					simulator.setChannel(channels[error]);
					
					simulator.simulate(8);
					
					bers[stream][method][error] = simulator.getBitErrorRate() * 100;
					desyncs[stream][method][error] = simulator.getDesyncsCount();
				}
			}
		}

		for (int stream = 0; stream < streams.length; stream++) {
			for (int errorType = 0; errorType < 3; errorType++) {
				out.println(fileNames[stream] + " - " + errorTypes[errorType]);
				
				out.print(errorTypeColumns[errorType]);
				
				for (int method = 0; method < transmitters.length; method++)
					out.print(";" + methodNames[method]);
				
				out.println();
				
				for (int error = 0; error < 4; error++) {
					out.print(xValues[errorType * 4 + error]);
					
					for (int method = 0; method < transmitters.length; method++)
						out.print(";" + bers[stream][method][errorType * 4 + error]);
					
					out.println();
				}
			}
			
			out.println(fileNames[stream] + " - Desynchronizacje");
			
			out.println("Metoda;Liczba desynchronizacji");
			
			for (int method = 0; method < transmitters.length; method++)
				out.println(methodNames[method] + ";" + desyncs[stream][method][0]);
		}
		
		out.close();
	}
}
