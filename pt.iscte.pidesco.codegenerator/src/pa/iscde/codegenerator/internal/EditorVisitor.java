package pa.iscde.codegenerator.internal;


import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * This class is responsible to inspect the file and set the necessary information in the model
 * @author D01
 *
 */
public class EditorVisitor extends ASTVisitor{
	private final CodeGeneratorModel codeGeneratorModel;
	private final String methodSearchExpression;
	private final String variableSearchExpression;
	private final int line;

	/**
	 * This constructor is used in case there's no need to search for a specific term
	 * @param codeGeneratorModel Model where the visitor will set the information.
	 */
	public EditorVisitor(CodeGeneratorModel codeGeneratorModel) {
		this.codeGeneratorModel = codeGeneratorModel;
		this.methodSearchExpression = null;
		this.variableSearchExpression = null;
		this.line = -1;
	}

	/**
	 * This constructor is used in case there's the need to search for a specific term
	 * @param codeGeneratorModel Model where the visitor will set the information
	 * @param variableSearchExpression expression to be used by the ASTVisitor to search for a specific variable
	 * @param methodSearchExpression expression to be used by the ASTVisitor to search for a specific method
	 */ 
	public EditorVisitor(CodeGeneratorModel codeGeneratorModel, String methodSearchExpression, String variableSearchExpression) {
		this.codeGeneratorModel = codeGeneratorModel;
		this.methodSearchExpression = methodSearchExpression;
		this.variableSearchExpression = variableSearchExpression;
		this.line = -1;
	}

	/**
	 * This constructor is used in case there's the need to search for a specific term in a specific line
	 * @param codeGeneratorModel Model where the visitor will set the information
	 * @param variableSearchExpression expression to be used by the ASTVisitor to search for a specific variable
	 * @param methodSearchExpression expression to be used by the ASTVisitor to search for a specific method
	 * @param line the line which the visitor will check before setting the model information
	 */ 
	public EditorVisitor(CodeGeneratorModel codeGeneratorModel, String methodSearchExpression, String variableSearchExpression, 
			int line) {
		this.codeGeneratorModel = codeGeneratorModel;
		this.methodSearchExpression = methodSearchExpression;
		this.variableSearchExpression = variableSearchExpression;
		this.line = line;
	}

	private int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
	}

	private int endLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition() + node.getLength());
	}	

	//visits class and sets in the module the line where the class statement is and the offset of the end of the file
	@Override
	public boolean visit(TypeDeclaration node) {
		codeGeneratorModel.setClassInitLine(sourceLine(node));
		codeGeneratorModel.setEndOfFileLine(endLine(node));
		return true;
	}

	//visits methods and adds the method end offset to the model if method name matches the methodSearchExpression
	@Override
	public boolean visit(MethodDeclaration node) {
		if(methodSearchExpression != null && node.getName().toString().equals(methodSearchExpression)) {
			int endLine = endLine(node);
			codeGeneratorModel.setMethodEndLine(endLine);
		}
		return true;
	}
	// visits fields and adds to the model the last field line
	@Override
	public boolean visit(FieldDeclaration node) {
		codeGeneratorModel.setFieldEndLine(sourceLine(node));
		return true; 
	}

	/**
	 *Visits variable declarations and if it matches the variableSearchExpression, sets the methodType in the model, otherwise sets
	 *methodType to void
	 *It also adds the variable offset in the model in case the sourceline matches the field line
	 */
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if(variableSearchExpression != null && !variableSearchExpression.equals("")) {
			Expression expression = node.getInitializer();
			String initializer = "";
			String expressionType = "void";
			if(expression != null) {
				initializer = expression.toString();
			}
			if(initializer.equals(variableSearchExpression)) {
				ASTNode nodeParent = node.getParent();
				if(nodeParent instanceof VariableDeclarationStatement) {
					expressionType = ((VariableDeclarationStatement) nodeParent).getType().toString();
				}
				else if(nodeParent instanceof FieldDeclaration) {
					expressionType = ((FieldDeclaration) nodeParent).getType().toString();
				}
				codeGeneratorModel.setMethodType(expressionType);
			}
			if(line == -1 || line == sourceLine(node)) {
				codeGeneratorModel.setVariableOffset(node.getStartPosition() + expressionType.length());
			}
			return true;
		}
		return false;
	}
}
