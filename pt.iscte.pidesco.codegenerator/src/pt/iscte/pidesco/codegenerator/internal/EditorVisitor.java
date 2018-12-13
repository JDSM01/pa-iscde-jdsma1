package pt.iscte.pidesco.codegenerator.internal;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * This class is responsible to inspect the file and set the necessary information in the model
 * @author D01
 *
 */
public class EditorVisitor extends ASTVisitor{
	private final CodeGeneratorModel codeGeneratorModel;
	private final String searchExpression;

	/**
	 * This constructor is used in case there's no need to search for a specific term
	 * @param codeGeneratorModel Model where the visitor will set the information.
	 */
	public EditorVisitor(CodeGeneratorModel codeGeneratorModel) {
		this.codeGeneratorModel = codeGeneratorModel;
		this.searchExpression = null;
	}

	/**
	 * This constructor is used in case there's the need to search for a specific term
	 * @param codeGeneratorModel Model where the visitor will set the information
	 * @param searchExpression Expression to be used in the ASTVisitor methods
	 */ 
	public EditorVisitor(CodeGeneratorModel codeGeneratorModel, String searchExpression) {
		this.codeGeneratorModel = codeGeneratorModel;
		this.searchExpression = searchExpression;
	}

	//visits class
	@Override
	public boolean visit(TypeDeclaration node) {
		codeGeneratorModel.setEndOfFileOffset(node.getStartPosition() + node.getLength());
		return true;
	}

	//visits methods
	@Override
	public boolean visit(MethodDeclaration node) {
		if(node.getName().toString().equals(searchExpression)) {
			int endOffset = node.getStartPosition() + node.getLength();
			codeGeneratorModel.setConstructorEndOffset(endOffset);
		}
		return true;
	}
	// visits attributes
	@Override
	public boolean visit(FieldDeclaration node) {
		int endOffset = node.getStartPosition() + node.getLength();
		codeGeneratorModel.setFieldEndOffset(endOffset);
		return true; 
	}

	// visits variable declarations
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if(searchExpression != null) {
			Expression expression = node.getInitializer();
			String initializer = "";
			String expressionType = "";
			if(expression != null) {
				initializer = expression.toString();
			}
			if(initializer.equals(searchExpression) && node.getParent() instanceof FieldDeclaration) {
				expressionType = ((FieldDeclaration) node.getParent()).getType().toString();
				codeGeneratorModel.setMethodType(expressionType);
			}else {
				codeGeneratorModel.setMethodType("void");
			}
			return true;
		}
		return false;
	}
}
