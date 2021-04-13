FROM java:8
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
CMD ["java","-jar","Final_project_GUI.jar"]
