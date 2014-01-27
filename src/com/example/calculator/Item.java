package com.example.calculator;

import java.io.Serializable;

/**
 * @author Andrei. Element of list in ListView.
 */
public class Item implements Serializable {
	/**
	 * <p>
	 * The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization. If the receiver has loaded a class for the
	 * object that has a different serialVersionUID than that of the
	 * corresponding sender's class, then deserialization will result in an
	 * InvalidClassException. A serializable class can declare its own
	 * serialVersionUID explicitly by declaring a field named "serialVersionUID"
	 * that must be static, final, and of type long:
	 * </p>
	 * 
	 * ANY-ACCESS-MODIFIER static final long serialVersionUID = 42L;
	 * 
	 * <p>
	 * If a serializable class does not explicitly declare a serialVersionUID,
	 * then the serialization runtime will calculate a default serialVersionUID
	 * value for that class based on various aspects of the class, as described
	 * in the Java(TM) Object Serialization Specification. However, it is
	 * <b>strongly recommended</b> that all serializable classes explicitly
	 * declare serialVersionUID values, since the default serialVersionUID
	 * computation is highly sensitive to class details that may vary depending
	 * on compiler implementations, and can thus result in unexpected
	 * InvalidClassExceptions during deserialization. Therefore, to guarantee a
	 * consistent serialVersionUID value across different java compiler
	 * implementations, a serializable class must declare an explicit
	 * serialVersionUID value. It is also strongly advised that explicit
	 * serialVersionUID declarations use the private modifier where possible,
	 * since such declarations apply only to the immediately declaring class -
	 * serialVersionUID fields are not useful as inherited members.
	 * </p>
	 */
	private static final long serialVersionUID = 3718566432226753115L;
	/**
	 * Equation in element of list.
	 */
	private String equation;
	/**
	 * Result in element of list.
	 */
	private Double result;

	/**
	 * @return Equation contains in element of list.
	 */
	public final String getEquation() {
		return equation;
	}

	/**
	 * @param eq
	 *            Puts equation in element of list.
	 */
	public final void setEquation(final String eq) {
		this.equation = eq;
	}

	/**
	 * @return Result contains in element of list.
	 */
	public final Double getResult() {
		return result;
	}

	/**
	 * @param res
	 *            Puts result in element of list.
	 */
	public final void setResult(final Double res) {
		this.result = res;
	}
}
