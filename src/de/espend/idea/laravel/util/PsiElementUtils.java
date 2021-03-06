package de.espend.idea.laravel.util;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.utils.PhpElementsUtil;
import fr.adrienbrault.idea.symfony2plugin.util.ParameterBag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class PsiElementUtils {
    /**
     * getChildren fixed helper
     */
    static public PsiElement[] getChildrenFix(PsiElement psiElement) {
        List<PsiElement> psiElements = new ArrayList<>();

        PsiElement startElement = psiElement.getFirstChild();
        if(startElement == null) {
            return new PsiElement[0];
        }

        psiElements.add(startElement);

        for (PsiElement child = psiElement.getFirstChild().getNextSibling(); child != null; child = child.getNextSibling()) {
            psiElements.add(child);
        }

        return psiElements.toArray(new PsiElement[psiElements.size()]);
    }

    @Nullable
    public static String trimQuote(@Nullable String text) {

        if(text == null) return null;

        return text.replaceAll("^\"|\"$|\'|\'$", "");
    }

    public static boolean isFunctionReference(@NotNull PsiElement psiElement, @NotNull  String functionName,  int parameterIndex) {

        PsiElement parameterList = psiElement.getParent();
        if(!(parameterList instanceof ParameterList)) {
            return false;
        }

        ParameterBag index = PhpElementsUtil.getCurrentParameterIndex(psiElement);
        if(index == null || index.getIndex() != parameterIndex) {
            return false;
        }

        PsiElement functionCall = parameterList.getParent();
        if(!(functionCall instanceof FunctionReference)) {
            return false;
        }

        return functionName.equals(((FunctionReference) functionCall).getName());
    }

}
