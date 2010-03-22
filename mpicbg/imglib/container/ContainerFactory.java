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
 *
 * @author Stephan Preibisch & Stephan Saalfeld
 */
package mpicbg.imglib.container;

import mpicbg.imglib.Factory;
import mpicbg.imglib.container.basictypecontainer.*;
import mpicbg.imglib.type.Type;

public abstract class ContainerFactory implements Factory
{
	protected boolean useOptimizedContainers = true;
		
	public void setOptimizedContainerUse ( final boolean useOptimizedContainers ) { this.useOptimizedContainers = useOptimizedContainers; }
	public boolean useOptimizedContainers() { return useOptimizedContainers; }
	
	// All basic Type containers
	public abstract <T extends Type<T>> Container<T, ? extends BitAccess> createBitInstance( int[] dimensions, int entitiesPerPixel );
	public abstract <T extends Type<T>> Container<T, ? extends ByteAccess> createByteInstance( int[] dimensions, int entitiesPerPixel );
	public abstract <T extends Type<T>> Container<T, ? extends CharAccess> createCharInstance( int[] dimensions, int entitiesPerPixel );
	public abstract <T extends Type<T>> Container<T, ? extends ShortAccess> createShortInstance( int[] dimensions, int entitiesPerPixel );
	public abstract <T extends Type<T>> Container<T, ? extends IntAccess> createIntInstance( int[] dimensions, int entitiesPerPixel );
	public abstract <T extends Type<T>> Container<T, ? extends LongAccess> createLongInstance( int[] dimensions, int entitiesPerPixel );
	public abstract <T extends Type<T>> Container<T, ? extends FloatAccess> createFloatInstance( int[] dimensions, int entitiesPerPixel );
	public abstract <T extends Type<T>> Container<T, ? extends DoubleAccess> createDoubleInstance( int[] dimensions, int entitiesPerPixel );
}
