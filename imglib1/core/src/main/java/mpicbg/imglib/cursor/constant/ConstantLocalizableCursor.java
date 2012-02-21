/**
 * Copyright (c) 2009--2012, Tobias Pietzsch, Stephan Preibisch & Stephan Saalfeld
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
package mpicbg.imglib.cursor.constant;

import mpicbg.imglib.container.constant.ConstantContainer;
import mpicbg.imglib.cursor.LocalizableCursor;
import mpicbg.imglib.image.Image;
import mpicbg.imglib.type.Type;

/**
 * A simple LocalizableCursor that always returns the same value at each location, but iterates the right amount of
 * pixels relative to its size and localizes itself properly.
 * 
 * @author Stephan Preibisch
 *
 * @param <T>
 */
public class ConstantLocalizableCursor< T extends Type< T > > extends ConstantCursor< T > implements LocalizableCursor< T >
{
	final protected int[] position, dimensions;
	
	public ConstantLocalizableCursor( final ConstantContainer<T> container, final Image<T> image, final T type )
	{
		super( container, image, type );
		
		this.dimensions = image.getDimensions();
		this.position = new int[ numDimensions ];
		
		reset();
	}

	@Override
	public void fwd()
	{ 
		++i; 
		
		for ( int d = 0; d < numDimensions; d++ )
		{
			if ( position[ d ] < dimensions[ d ] - 1 )
			{
				position[ d ]++;
				
				for ( int e = 0; e < d; e++ )
					position[ e ] = 0;
				
				return;
			}
		}
	}

	@Override
	public void fwd( final long steps )
	{ 
		for ( long j = 0; j < steps; ++j )
			fwd();
	}
	

	@Override
	public void reset()
	{
		i = -1;
		position[ 0 ] = -1;
		
		for ( int d = 1; d < numDimensions; d++ )
			position[ d ] = 0;		
	}

	@Override
	public void getPosition( int[] position )
	{
		for ( int d = 0; d < numDimensions; d++ )
			position[ d ] = this.position[ d ];
	}
	
	@Override
	public int[] getPosition(){ return position.clone(); }
	
	@Override
	public int getPosition( final int dim ){ return position[ dim ]; }
	
	@Override
	public String getPositionAsString()
	{
		String pos = "(" + position[ 0 ];
		
		for ( int d = 1; d < numDimensions; d++ )
			pos += ", " + position[ d ];
		
		pos += ")";
		
		return pos;
	}
	
	@Override
	public String toString() { return getPositionAsString() + " = " + getType(); }		
}