package com.example.firstPlugin;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vcs.*;
import com.intellij.openapi.vcs.actions.VcsContextFactory;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CurrentContentRevision;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.templates.github.GithubTagInfo;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import git4idea.*;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;

import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenerateGithub extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        DialogGithub dialog1 = new DialogGithub();
        dialog1.setBounds(100,100,500,500);

        //get git information
        VirtualFile[] sourceRoots = ModuleRootManager.getInstance(ModuleManager.getInstance(e.getProject()).getModules()[0]).getSourceRoots();
        for (VirtualFile file : sourceRoots) {
            //System.out.println(file.getPath());
        }

        //gets the path root of the project
        VirtualFile contentRoot = ModuleRootManager.getInstance(ModuleManager.getInstance(e.getProject()).getModules()[0]).getContentRoots()[0];
        dialog1.setLabel2(contentRoot.getPath());

        //this one gets the current open file
        VirtualFile[] virtualFiles = e.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
        for (VirtualFile file : virtualFiles) {
            //System.out.println(file.getPath());

        }

        //gets all the children (directories and files) of the passed root
        List<VirtualFile> virtualFileList = VfsUtil.collectChildrenRecursively(contentRoot);
        for (VirtualFile file : virtualFileList) {
            if (!file.isDirectory()) {
                System.out.println(file.getPath());
            }
            //file.
        }


        ProjectLevelVcsManager plvm = ProjectLevelVcsManager.getInstance(e.getProject());
        AbstractVcs vcs = plvm.getVcsFor(contentRoot);

        GitRepositoryManager gitRepositoryManager = ServiceManager.getService(e.getProject(), GitRepositoryManager.class);
        GitRepository repository2 = gitRepositoryManager.getRepositoryForRoot(contentRoot);
        GitLocalBranch currentBranch = repository2.getCurrentBranch();
        System.out.println("branch1 " + currentBranch);


        GitRepository repository = (GitRepository) GitUtil.getRepositoryManager(e.getProject()).getRepositoryForRoot(contentRoot);
        System.out.println("branch2 " + repository);


        System.out.println("vcs" + vcs);
        GitRepository repository3 = (GitRepository) GitUtil.getRepositoryManager(e.getProject()).getRepositoryForRoot(contentRoot);
        System.out.println("branch2 " + repository3);
        GitRepository repository1 = GitUtil.getRepositoryManager(e.getProject()).getRepositoryForRoot(contentRoot);
        repository1.getCurrentBranch();



        //GitRemote remote = getRemote(repository.getRemotes(), application.deployment.url);
        //GitRemoteBranch branch = getBranch(repository, remote);


        //vcs.getCheckinEnvironment().commit(changes, "Commit " + virtualFile.getNameWithoutExtension());

        /*VcsContextFactory contextFactory = PeerFactory.getInstance().getVcsContextFactory();
        FilePath path = contextFactory.createFilePathOn(contentRoot);
        List<Change> changes = new ArrayList<Change>();
        CurrentContentRevision cr = new CurrentContentRevision(path);
        Change c = new Change(cr, cr);
        changes.add(c);
        vcs.getCheckinEnvironment().commit(changes, "Commit " + virtualFile.getNameWithoutExtension());*/



        dialog1.setLabel1("Hola");
        dialog1.setVisible(true);
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
