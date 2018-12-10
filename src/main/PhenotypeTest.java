

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhenotypeTest {

	@Test
	void testIsAll() {
		Genotype g = new Genotype(5, 2);
		Phenotype ph = new Phenotype(g);
		double [] array1 = {0, 0, 0, 0, 0};
		double [] array2 = {1, 0, 0, 0, 0};
		double [] array3 = {1, 1, 1, 1, 1};
		assertTrue(ph.isAll(array1, 0, 1, 5));
		assertTrue(ph.isAll(array2, 0, 2, 5));
		assertFalse(ph.isAll(array3, 0, 1, 5));
	}

	@Test
	void testTransform() {
		Genotype g = new Genotype(5, 2);
		Phenotype ph = new Phenotype(g);
		assertEquals(ph.transform(2.0, 10.0, 0.01), 8.0);
		assertEquals(ph.transform(3.0, 10.0, 0.01), 10.0);
		assertEquals(ph.transform(-2.0, 10.0, 0.01), -8.0);
		assertEquals(ph.transform(-3.0, 10.0, 0.01), -10.0);
		assertEquals(ph.transform(0.1, 10.0, 0.01), 0.0);
		assertEquals(ph.transform(-0.1, 10.0, 0.01), 0.0);
	}

}
