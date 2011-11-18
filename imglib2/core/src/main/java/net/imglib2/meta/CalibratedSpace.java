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

package net.imglib2.meta;

import net.imglib2.EuclideanSpace;
import net.imglib2.img.Axis;
import net.imglib2.img.Img;

/**
 * A Euclidean space whose dimensions have names and calibrations.
 * 
 * @author Lee Kamentsky
 */
public interface CalibratedSpace extends EuclideanSpace {

	/** Gets the dimensional index of the axis with the given type. */
	int getAxisIndex(final Axis axis);

	/** Gets the associated {@link Img}'s axis at the given dimension. */
	Axis axis(int d);

	/** Copies the {@link Img}'s axes into the given array. */
	void axes(Axis[] axes);

	/** Sets the dimensional axis for the given dimension. */
	void setAxis(Axis axis, int d);

	/** Gets the associated {@link Img}'s calibration at the given dimension. */
	double calibration(int d);

	/** Copies the {@link Img}'s calibration into the given array. */
	void calibration(double[] cal);

	/** Sets the image calibration for the given dimension. */
	void setCalibration(double cal, int d);

}
