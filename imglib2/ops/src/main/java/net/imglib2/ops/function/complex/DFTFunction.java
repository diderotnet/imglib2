/*

Copyright (c) 2011, Barry DeZonia.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 * Neither the name of the Fiji project developers nor the
    names of its contributors may be used to endorse or promote products
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */

package net.imglib2.ops.function.complex;

import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.ops.DiscreteNeigh;
import net.imglib2.ops.Function;
import net.imglib2.ops.Neighborhood;
import net.imglib2.ops.operation.binary.complex.ComplexAdd;
import net.imglib2.ops.operation.binary.complex.ComplexMultiply;
import net.imglib2.ops.operation.unary.complex.ComplexExp;
import net.imglib2.type.numeric.ComplexType;
import net.imglib2.type.numeric.complex.ComplexDoubleType;

// example implementation of a Discrete Fourier Transform function
//   - textbook definitions and thus SLOW

/**
 * 
 * @author Barry DeZonia
 * 
 */
public class DFTFunction<T extends ComplexType<T>>
	implements Function<long[], T>
{
	// -- instance variables --

	private Function<long[], T> spatialFunction;
	private long[] span;
	private long[] negOffs;
	private long[] posOffs;
	private DiscreteNeigh neighborhood;
	private ComplexImageFunction<ComplexDoubleType,ComplexDoubleType> dataArray;

	// -- temporary per instance working variables --
	private final ComplexAdd<T,T,T> adder;
	private final ComplexExp<T,T> exper;
	private final ComplexMultiply<T,T,T> multiplier;

	private final T MINUS_TWO_PI_I;
	private final T constant;
	private final T expVal;
	private final T funcVal;
	private final T spatialExponent;

	private ComplexDoubleType tmp;

	private final T type;

	// -- constructor --

	public DFTFunction(Function<long[], T> spatialFunction, long[] span,
			long[] negOffs, long[] posOffs, T type) {
		if (span.length != 2)
			throw new IllegalArgumentException(
					"DFTFunction is only designed for two dimensional functions");

		this.type = type;

		this.tmp = new ComplexDoubleType();

		this.adder = new ComplexAdd<T,T,T>();
		this.exper = new ComplexExp<T,T>();
		this.multiplier = new ComplexMultiply<T,T,T>();
		
		this.spatialFunction = spatialFunction;
		this.span = span.clone();
		this.negOffs = negOffs.clone();
		this.posOffs = posOffs.clone();
		this.neighborhood = new DiscreteNeigh(span.clone(), this.negOffs,
				this.posOffs);

		this.MINUS_TWO_PI_I = createOutput();
		this.constant = createOutput();
		this.expVal = createOutput();
		this.funcVal = createOutput();
		this.spatialExponent = createOutput();

		this.MINUS_TWO_PI_I.setComplexNumber(0, -2 * Math.PI);

		this.dataArray = createDataArray();
	}

	// -- public interface --

	@Override
	public void evaluate(Neighborhood<long[]> neigh, long[] point, T output) {
		dataArray.evaluate(neigh, point, tmp);
		output.setComplexNumber(tmp.getRealDouble(), tmp.getImaginaryDouble());
	}

	@Override
	public DFTFunction<T> copy() {
		return new DFTFunction<T>(spatialFunction.copy(), span, negOffs,
				posOffs, type);
	}

	@Override
	public T createOutput() {
		return type.createVariable();
	}

	// -- private helpers --

	// TODO - use a ComplexImageAssignment here instead? Speed. Elegance?

	private ComplexImageFunction<ComplexDoubleType,ComplexDoubleType> createDataArray() {
		// TODO - this factory is always an array in memory with corresponding
		// limitations
		final ImgFactory<ComplexDoubleType> imgFactory = new ArrayImgFactory<ComplexDoubleType>();
		final Img<ComplexDoubleType> img = imgFactory.create(span,
				new ComplexDoubleType());
		final RandomAccess<ComplexDoubleType> oAccessor = img.randomAccess();
		final long[] iPosition = new long[2];
		final long[] oPosition = new long[2];
		final T sum = createOutput();
		final T xyTerm = createOutput();
		for (int ox = 0; ox < span[0]; ox++) {
			oPosition[0] = ox;
			for (int oy = 0; oy < span[1]; oy++) {
				oPosition[1] = oy;
				sum.setZero();
				for (int ix = 0; ix < span[0]; ix++) {
					iPosition[0] = ix;
					for (int iy = 0; iy < span[1]; iy++) {
						iPosition[1] = iy;
						calcTermAtPoint(oPosition, iPosition, xyTerm);
						adder.compute(sum, xyTerm, sum);
					}
				}
				oAccessor.setPosition(oPosition);
				oAccessor.get().setComplexNumber(sum.getRealDouble(),
						sum.getImaginaryDouble());
			}
		}
		return new ComplexImageFunction<ComplexDoubleType,ComplexDoubleType>(img,
				new ComplexDoubleType());
	}

	private void calcTermAtPoint(long[] oPosition, long[] iPosition, T xyTerm) {
		neighborhood.moveTo(iPosition);
		spatialFunction.evaluate(neighborhood, iPosition, funcVal);
		double val = ((double) oPosition[0]) * iPosition[0] / span[0];
		val += ((double) oPosition[1]) * iPosition[1] / span[1];
		spatialExponent.setComplexNumber(val, 0);
		multiplier.compute(MINUS_TWO_PI_I, spatialExponent, constant);
		exper.compute(constant, expVal);
		multiplier.compute(funcVal, expVal, xyTerm);
	}
}
