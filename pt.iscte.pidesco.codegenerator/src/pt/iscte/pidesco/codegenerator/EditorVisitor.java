package pt.iscte.pidesco.codegenerator;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class EditorVisitor extends ASTVisitor{
	private final CodeGeneratorModel codeGeneratorModel;
	private final String searchExpression;
	private boolean methodVisit;

	public EditorVisitor(CodeGeneratorModel codeGeneratorModel) {
		this.codeGeneratorModel = codeGeneratorModel;
		this.searchExpression = null;
		methodVisit = true;
	}

	public EditorVisitor(CodeGeneratorModel codeGeneratorModel, String searchExpression) {
		this.codeGeneratorModel = codeGeneratorModel;
		this.searchExpression = searchExpression;
		methodVisit = true;
	}

	//visits class
	@Override
	public boolean visit(TypeDeclaration node) {
		codeGeneratorModel.setEndOfFile(node.getStartPosition() + node.getLength());
		return true;
	}
	
	//visits methods
	@Override
	public boolean visit(MethodDeclaration node) {
		if(methodVisit) {
			int endOffset = node.getStartPosition() + node.getLength();
			codeGeneratorModel.setConstructorEndOffset(endOffset);
			methodVisit = false;
		}
		return false;
	}
	// visits attributes
	@Override
	public boolean visit(FieldDeclaration node) {
		int endOffset = node.getStartPosition() + node.getLength();
		System.out.println(endOffset);
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
			System.out.println(initializer + " :: " + searchExpression);
			if(initializer.equals(searchExpression) && node.getParent() instanceof FieldDeclaration) {
					expressionType = ((FieldDeclaration) node.getParent()).getType().toString();
					codeGeneratorModel.setExpressionType(expressionType);
			}
			return true;
		}
		return false;
	}
}
