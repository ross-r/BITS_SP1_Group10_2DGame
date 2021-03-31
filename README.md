### Prerequisites
1.  Download any git command line tool or git user-interface tool, I recommend git bash and the following documentation will be based on git bash (cli).
Git bash: [https://gitforwindows.org/](https://gitforwindows.org/)

2. Once git bash has been installed, depending on your installation choices you may access git commands from command prompt or from MingW, if you opted to use MingW then you will have a search menu result for "Git Bash", this is your CLI.

3. Navigate to the releases page for the repository and download the LWJGL3.zip file from the first release. [[click here]](https://github.com/RossRRMIT/BITS_SP1_Group10_2DGame/releases/tag/0.0.0 "[click here]")

### Cloning the repository
1. Navigate to the repository and click the "Code" button in the top right, then click "HTTPS" and copy the link.

	![How to get the clone URL](https://i.imgur.com/rXW0uud.png "How to get the clone URL")

2. Open your Git CLI, you will need to navigate to a folder you want to clone into.
To do this use the `cd` command (change directory)

		cd ~
		cd Documents

3. Now you can clone the repository

		git clone https://github.com/RossRRMIT/BITS_SP1_Group10_2DGame.git

This will create a folder called `BITS_SP1_Group10_2DGame` inside of this folder is the repository, now you can navigate into it.

		cd BITS_SP1_Group10_2DGame

To make sure everything is working properly, execute the following:

		git status

Your output should be:

		On branch master
		Your branch is up to date with 'origin/master'.
		
		nothing to commit, working tree clean

### Committing changes
In order to commit a change there needs to be a change, for the sake of this tutorial we will create a random text file and push it to the repository, this will be replicated locally and on the remote repository, everyone will be able to see these changes.

To create a file we can use the command `touch filename.txt`
!! MAKE SURE TO NAME THE FILE SOMETHING COMPLETELY RANDOM !!
`touch superepicfile.txt`

This file will now exist inside the local repository (on your machine) but it is not yet present in the remote repository so others cannot see it.

To commit a change we need to perform a few commands.

		git add .
		OR
		git add superepicfile.txt <-- allows you to commit specific files, repeat for each file

Before we can push the change to the repository we need to make a commit message.

		git commit -m "Committing my local file"

### Pushing changes
Before we can push any changes we need to setup our identity with a few simple commands, if you are using a GUI this may have already been done for you or you may have been prompted to log into github.

		git config --global user.name "John Doe"
		git config --global user.email johndoe@example.com

Make sure you populate these fields with the same creditionals you are using on github, if you registered to github with your student email you would supply your student email.

Now we can work on pushing changes.

You will firstly need to set the remote destination, this includes the branch you will push your changes to, by default this branch is the 'master' branch.

		git push --set-upstream origin master

This will push your changes to the remote repository and set the default upstream to origin/master where `origin` is the repository and `master` is the branch.

You may be asked to log into your github account at this point, this is something I cannot document as it may be different for everyone. I would recommend setting up an SSH key and using that, however, a simple google search will document this better than I can.

### Pulling changes
Pulling changes is much like pushing and since we have already done all the setup when we pushed our changes, all we need to do is execute a single command to pull.

		git pull

This will attempt to pull the remote changes into your local repository, this may also fail with a merge conflict, if a merge conflict occurs your local repository is placed into a merging branch and you will need to resolve the conflicts and commit them locally before you can push the resolved conflicts.

Conflicts will be marked in source code files with a tag, something like:

		<---hash => master
		... code from 'head' commit on remote
		---
		... code from your local repository
		--->

To resolve these conflicts you will need to manually merge the changes that are conflicting.
If you need more help with this please contact your peers to make sure you do not overwrite someone elses changes.

------------

### Setting up the project in Eclipse
1. Open Eclipse IDE and navigate the file menu.
File -> Open Projects from File System...

2.  Once the dialog opens click on "Directory..." next to the "Import source" box.

3. Navigate to the folder that you cloned the repository into and select the Game folder.
It should look something like this:
![](https://i.imgur.com/7lKEKxL.png)
The only difference is that I have already imported the project, you can just click the "Finish" button.

------------


#### Now you need to include the LWJGL3 binaries that you downloaded earlier.

1.  Extract the zip file in the same folder that has "README.md", this should create a folder called "LWJGL3" with all the .jar files and licenses.

2. The project may automatically recognize the LWJGL3 folder as an external resource and you may be able to run the project without errors at this point, if not, following below steps.

3. Right click the project, "Build Path" -> "Add External Archives"
![](https://i.imgur.com/YViGoY4.png)

4. Navigate to the LWJGL3 folder and select all the .jar files (CTRL+A) and click "Open"

5. You should now be able to run the project and it should produce a window with the title "Hello World!" and a red background.

------------

You are now all set up and ready to work!