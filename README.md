# CS1660-CloudComputing_FinalProject-2

By: Prayush Luitel

## The java source code was created for use on my local machine and using my GCP project. Therefore, some changes to the source code must be made. Follow these steps to alter the source code and run the client application, InvertedIndex map reduce, and Top N map reduce java programs. 

## My implementation of this course project relies on precomputed outputs of InvertedIndex and TopN applications on the GCP dataproc cluster and the output stored on the cloud storage bucket so therefore start with the following steps.
1)	Unzip the data files and cd into the directory that holds the three data files Hugo.tar.gz, Shakespeare.tar.gz, and Tolstoy.tar.gz
2)	Extract/Unzip all three data files provided for this project using the “tar -xf” command on command prompt as follows:
  <br /> a.	tar -xf Hugo.tar.gz
  <br /> b.	 tar -xf Shakespeare.tar.gz
  <br /> c.	Tar -xf Tolstoy.tar.gz
3)	You will now see a folder for each of these files in your current directory.
4)	Store all three folders in another folder called all_files.
5)	Store pairs of folders into another folder call them as follows:
  <br /> a.	Hugo_Shakespeare that stores the Hugo folder and the Shakespeare folder.
  <br /> b.	Hugo_Tolstoy that stores the Hugo folder and the Tolstoy folder.
  <br /> c.	Shakespeare_Tolstoy that stores the Shakespeare folder and Tolstoy folder
6)	Sign into the gcp console
7)	From the gcp console enable the API for both dataproc and Cloud Storage.
8)	In the Cloud Storage, please create one bucket where you intend to store all of the input and output files for the Hadoop mapreduce jobs.
  <br /> a.	For simplicity I will call this bucket “my_bucket” but feel free to name it whatever works
9)	Through the Console UI for Cloud Storage, please upload the folder that was previously named all_files from your local machine.
10)	Through the Console UI for Cloud Storage, please upload the three folders that were created in step 5) from your local machine.
11)	Also, through the Console UI for Cloud Storage, please upload the InvertedIndex.jar file and TopN.jar file I have added to this git Repository
12)	In the Dataproc UI please have a cluster with 1 master node and 2 worker nodes running or create a cluster with 1 master node and 2 worker nodes.
  <br /> a.	For simplicity the cluster name will be “my_cluster”
13)	Now we will submit 7 distinct jobs using the InvertedIndex.jar source code I have provided and 7 distinct jobs using the TopN.jar source code I have provided through the Console UI and within the running cluster as follows(follow the naming convention to reduce the amount of source code to change later on):
  <br /> a.	Click on submit jobs-> Specify cluster as my_cluster -> Specify job type as Hadoop -> Specify Main class or jar as InvertedIndex-> Specify Jar files as the location of your Inverted Index jar file(ex. gs://my_bucket/InvertedIndex.jar)-> Specify input argument as the location of the input directory of all_files and subsequent folders (ex. gs://my_bucket/all_files/*/*(Note the */* it is important)), Specify the output argument as the directory that you want to store the output results(for all files make the output argument as follows: gs://my_bucket/all_files_invertedIndex_opt) all under the Arguments section.
  <br /> b.	Follow the same step as a) but this time as input the directory that holds the folder Hugo and specify the output as: gs://my_bucket/ hugo_invertedindex_opt
  <br /> c.	Follow the same step as a) but this time as input the directory that holds the folder Shakespeare and specify the output as: gs://my_bucket/ shakespeare_inverted_index_opt
  <br /> d.	Follow the same step as a) but this time as input the directory that holds the folder Tolstoy and specify the output as: gs://my_bucket/tolstoy_invertedindex_opt
  <br /> e.	Follow the same step as a) but this time as input the directory that holds the folder Shakespeare and Hugo and specify the output as: gs://my_bucket/ Hugo_Shakespeare_inverted_index_opt
  <br /> f.	Follow the same step as a) but this time as input the directory that holds the folder Shakespeare and Tolstoy and specify the output as: gs://my_bucket/ Shakespeare_Tolstoy_inverted_index_opt
  <br /> g.	Follow the same step as a) but this time as input the directory that holds the folder Hugo and Tolstoy and specify the output as: gs://my_bucket/ Hugo_Tolstoy_inverted_index_opt
  <br /> h.	Now, Click on submit jobs-> Specify cluster as my_cluster -> Specify job type as Hadoop -> Specify Main class or jar as TopN-> Specify Jar files as the location of your TopN jar file(ex. gs://my_bucket/InvertedIndex.jar)-> Specify input argument as the location of the output directory of inverted index for all_files (ex. gs://my_bucket/all_files_invertedIndex_opt), Specify the output argument as the directory that you want to store the output results(for all files make the output argument as follows: gs://my_bucket/all_files_topN) all under the Arguments section.
  <br /> i.	Follow the same step as h) but this time as input the directory that holds the output of inverted index for Shakespeare and set the output as gs://my_bucket/shakespeare_top_N_opt
  <br /> j.	Follow the same step as h) but this time as input the directory that holds the output of inverted index for Tolstoy and set the output as gs://my_bucket/tolstoy_top_N_opt
  <br /> k.	Follow the same step as h) but this time as input the directory that holds the output of inverted index for Hugo and set the output as gs://my_bucket/hugo_top_N_opt
  <br /> l.	Follow the same step as h) but this time as input the directory that holds the output of inverted index for Hugo and Shakespeare and set the output as gs://my_bucket/Hugo_Shakespeare_top_n_opt
  <br /> m.	Follow the same step as h) but this time as input the directory that holds the output of inverted index for Hugo and Tolstoy and set the output as gs://my_bucket/Hugo_Tolstoy_top_n_opt
  <br /> n.	Follow the same step as h) but this time as input the directory that holds the output of inverted index for Shakespeare and Tolstoy and set the output as gs://my_bucket/Shakespeare_Tolstoy_top_n_opt
14)	Now we have all the possible inverted indices and Top N outputs stored in folders within out Cloud Storage

## Furthermore, Now that all the output files necessary are available through cloud storage, we can move on to a few changes that need to be made to the code.
1)	Make sure that your GCP account is authenticated, and you have downloaded the resulting .json file using steps form this link.
  <br /> a.	https://cloud.google.com/docs/authentication/getting-started
2)	 Store the .json file in the same directory that you have the unextracted data files and the client GUI source code called Final_project_GUI.java.
3)	In the java file change the Json_Name field to the name of your .json file, change the name of the ProjectId to the name of your project Id(This can be accessed through the GCP console when looking at details of your project), the bucketName to the name of your bucket(ex. my_bucket) all within the quotations for each String variable.
4)	If you followed the naming convention stated above you will not have to change the objectName for the if statements in the source code. Otherwise please change the object name to match the output folders that you have created while leaving the /part-r-0000 part untouched.

## Next, we will create a Runnable jar file for the Client GUI source code:
1)	If the file is open in eclipse IDE, please add the appropriate libraries required including JRE 1.8 system library and the Google Cloud Platform Client library(download cloud tools for eclipe from marketplace and add the library for cloud storage to the project) to the buildpath. Otherwise please also include these libraries to your path when building the Runnable JAR file.
2)	In eclipse, please export the folder as a Runnable Jar file and set the destination as the same directory that holds the downloaded .json file and the data files. Otherwise please create a Runnable jar file within the same directory that holds the .json file and the data files while making sure you have included the appropriate libraries.

## Next we will build a docker image for the GUI and run it through a docker container:
1)	Please download the dockerfile that I have uploaded to the repository and store it with the datafiles, .json file, and the Runnable Jar file.
2)	Make sure docker desktop is running. If not start it up.
3)	Run the command to build a docker image while specifying the image name as you choose.
  <br /> a.	For simplicity I will use my_image as the name for the image
  <br /> b.	Command: docker build -t my_image .
4)	This will build an image for your gui using docker
5)	You will need to set a mount point to the local directory that has the data files when you run the docker image as follows:
  <br /> a.	-v C:\Users:\host\Users
  <br /> b.	-v specified a mount point and the left handside of the “:” symbol represents the local path and the right side represents the path that will appear on docker when you are choosing files.
6)	Follow the steps from the following link to run a gui in docker including finding a display:
  <br /> a.	https://cuneyt.aliustaoglu.biz/en/running-gui-applications-in-docker-on-windows-linux-mac-hosts/
7)	However, in the command to run the gui include the mount point as follows(make sure to have xlaunch/xquarts configured and running):
  <br /> a.	docker run -v C:\Users:\host\Users --rm -it -e DISPLAY=192.168.104.62:0.0 my_image

## Now you can run the gui from docker as intended!


# ****(For this project I have chosen to implement the extra credit option and return the results of the search and topN as a Jtable)*****
