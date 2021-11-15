package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits from Node class.
 * Consists of four or three {@link Element} parameters : variable,
 * startExpression, endExpression, stepExpression.
 * 
 * @author gorsicleo
 */
public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	/**Constructor.
	 * @param variable that is found inside for tag
	 * @param startExpression 
	 * @param endExpression
	 * @param stepExpression can be null!
	 * @throws NullPointerException if anything <b>except</b> stepExpression is null
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException();
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	public ElementVariable getVariable() {
		return variable;
	}

	public Element getStartExpression() {
		return startExpression;
	}

	public Element getEndExpression() {
		return endExpression;
	}

	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		String forLoopNodeString = new String("{$ FOR ");

		forLoopNodeString += variable + " " + startExpression + " " + endExpression + " ";
		forLoopNodeString = (stepExpression != null) ? forLoopNodeString + stepExpression : forLoopNodeString;
		forLoopNodeString += "$} ";

		for (int i = 0; i < numberOfChildren(); i++) {
			forLoopNodeString += getChild(i).toString();
		}

		return forLoopNodeString + "{$END$}";

	}

}
