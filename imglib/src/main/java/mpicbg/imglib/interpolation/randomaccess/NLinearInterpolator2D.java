/**
 * Copyright (c) 2009--2010, Stephan Preibisch & Stephan Saalfeld
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.  Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution.  Neither the name of the Fiji project nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package mpicbg.imglib.interpolation.randomaccess;

import mpicbg.imglib.RandomAccessible;
import mpicbg.imglib.type.numeric.NumericType;

/**
 * 
 * @param <T>
 *
 * @author Tobias Pietzsch, Stephan Preibisch and Stephan Saalfeld
 */
public class NLinearInterpolator2D< T extends NumericType< T > > extends NLinearInterpolator< T > 
{	
	protected NLinearInterpolator2D( final NLinearInterpolator2D< T > interpolator )
	{
		super( interpolator );
	}
	
	protected NLinearInterpolator2D( final RandomAccessible< T > randomAccessible, final T type )
	{
		super( randomAccessible, type );
	}

	protected NLinearInterpolator2D( final RandomAccessible< T > randomAccessible )
	{
		super( randomAccessible );
	}

	@Override
	final public int numDimensions() { return 2; }

	@Override
	protected void fillWeights()
	{
		final double w0 = position[ 0 ] - target.getLongPosition( 0 );
		final double w0Inv = 1.0d - w0;
		final double w1 = position[ 1 ] - target.getLongPosition( 1 );
		final double w1Inv = 1.0d - w1;

		weights[ 0 ] = w0Inv * w1Inv;
		weights[ 1 ] = w0    * w1Inv;
		weights[ 2 ] = w0Inv * w1;
		weights[ 3 ] = w0    * w1;
	}
	
	@Override
	public T get()
	{
		fillWeights();

		accumulator.set( target.get() );
		accumulator.mul( weights[ 0 ] );
		target.fwd( 0 );
		tmp.set( target.get() );
		tmp.mul( weights[ 1 ] );
		accumulator.add( tmp );
		target.fwd( 1 );
		tmp.set( target.get() );
		tmp.mul( weights[ 3 ] );
		accumulator.add( tmp );
		target.bck( 0 );
		tmp.set( target.get() );
		tmp.mul( weights[ 2 ] );
		accumulator.add( tmp );
		target.bck( 1 );

		return accumulator;
	}
	
	@Override
	public NLinearInterpolator2D< T > copy()
	{
		return new NLinearInterpolator2D< T >( this );
	}
}
