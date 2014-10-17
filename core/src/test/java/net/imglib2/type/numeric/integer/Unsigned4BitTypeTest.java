package net.imglib2.type.numeric.integer;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class Unsigned4BitTypeTest
{
	static ArrayImg< Unsigned4BitType, ? > img;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		
		img = new ArrayImgFactory< Unsigned4BitType >().create( new long[]{ 10, 20, 30 }, new Unsigned4BitType() );
	}

	/**
	 * Test method for {@link net.imglib2.type.numeric.integer.Unsigned4BitType}.
	 */
	@Test
	public void testSetRandom()
	{
		final Random rnd = new Random( 0 );

		for ( final Unsigned4BitType t : img )
		{
			final int v = rnd.nextInt( 16 );
			t.set( v );
			assertTrue( t.get() == v );
		}
	}
}
