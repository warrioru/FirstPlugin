package com.example.firstPlugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class GenerateGithub extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        DialogGithub dialog1 = new DialogGithub();
        dialog1.setBounds(100,100,500,500);
        dialog1.setLabel1("Hola");
        dialog1.show();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        e.getPresentation().setEnabled(psiClass != null);


    }

    private PsiClass getPsiClassFromContext(@NotNull AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            e.getPresentation().setEnabled(false);
            return null;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
        return psiClass;

    }
}
