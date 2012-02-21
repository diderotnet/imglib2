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

package net.imglib2.ops.operation.binary.complex;

import net.imglib2.ops.BinaryOperation;
import net.imglib2.ops.operation.unary.complex.ComplexExp;
import net.imglib2.ops.operation.unary.complex.ComplexLog;
import net.imglib2.type.numeric.ComplexType;
import net.imglib2.type.numeric.complex.ComplexDoubleType;

//Handbook of Mathematics and Computational Science, Harris & Stocker, Springer, 2006

/**
 * Sets a complex number output to the result of raising a complex number to a
 * complex power represented by a second complex number.
 * 
 * @author Barry DeZonia
 * 
 */
public final class ComplexPower<
		I1 extends ComplexType<I1>,
		I2 extends ComplexType<I2>,
		O extends ComplexType<O>>
	implements BinaryOperation<I1,I2,O>
{
	private final ComplexLog<I1,ComplexDoubleType> logFunc =
			new ComplexLog<I1,ComplexDoubleType>();
	private final
		ComplexMultiply<I2,ComplexDoubleType,ComplexDoubleType>
			mulFunc =	new ComplexMultiply<I2,ComplexDoubleType,ComplexDoubleType>();
	private final ComplexExp<ComplexDoubleType,O> expFunc =
			new ComplexExp<ComplexDoubleType,O>();

	private final ComplexDoubleType logA = new ComplexDoubleType();
	private final ComplexDoubleType zLogA = new ComplexDoubleType();

	@Override
	public O compute(I1 a, I2 z, O output) {
		logFunc.compute(a, logA);
		mulFunc.compute(z, logA, zLogA);
		expFunc.compute(zLogA, output);
		return output;
	}

	@Override
	public ComplexPower<I1,I2,O> copy() {
		return new ComplexPower<I1,I2,O>();
	}

}
