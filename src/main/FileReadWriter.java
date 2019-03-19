import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileReadWriter {
	static int fileCntr = 1;
	final int WRITE_CYCLE = 10;
	File wfile;
	Genotype [][] store;

	FileReadWriter(){
		while (true) {
			String writeFileName = "/tmp/trial" + String.valueOf(fileCntr) + ".txt";
			wfile = new File(writeFileName);
			if (!wfile.exists()) {
				break;
			}
			fileCntr ++;
		}
		this.store = new Genotype[WRITE_CYCLE][];
	}

	void putGenoType(int generation, Genotype [] genotypes) {
		if (generation != 0 && generation % WRITE_CYCLE == 0) {
			for (int i = 0; i < WRITE_CYCLE; i++) {
				writeFile(generation - WRITE_CYCLE + i, this.store[i]);
			}
		}
		int row = generation % WRITE_CYCLE;
		this.store[row] = genotypes;
	}

	void writeFile(int generation, Genotype [] store) {
		try {
			FileWriter filewriter = new FileWriter(this.wfile, true);
			writeGeneration(filewriter, generation, store);
			filewriter.close();
		} catch(IOException e) {
			System.err.println(e);
		}
	}

	void writeGeneration(FileWriter fw, int generation, Genotype [] store) {
		try {
			for (int idx = 0; idx < store.length; idx ++) {
				Genotype g = store[idx];
				fw.write("generation: " + generation + " index: " + idx + "," + g.fitness + "," + g.data.getAveRewRight() + "," + g.data.getAveRewLeft() + "," + g.genetag.generateType +"\n");
				fw.write("weights: ");
				for (int i = 0; i < g.weights.length; i++) {
					for (int j = 0; j < g.weights[0].length; j++) {
						if (i == g.weights.length -1 && j == g.weights[0].length - 1) {
							fw.write(g.weights[i][j]+"\n");
						} else {
							fw.write(g.weights[i][j]+",");
						}
					}
				}
				fw.write("rule: " + g.rule[0] + "," + g.rule[1] + "," + g.rule[2] + "," + g.rule[3] + "," + g.rule[4] + "\n");
			}
		} catch(IOException e) {
		      System.err.println(e);
		}
	}
}
