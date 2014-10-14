Logic Component
=====

The logic component offers these features

<b>LogicMain</b>
This class handles the processing of user's input and perform corresponding action to it.

To use it, simply type:
	
	LogicMain logic = new LogicMain();
	logic.processInput(someStringText)

When it detected...
- "ADD" operation, it will create a task based on the input string. On completion, it will return a LinkedList with the task that was added.
	- Sample input: <b>@!add</b> Buy Cake <b>@!description</b> It is Henry's birthday!!!
- "EDIT" operation, it will edit the task based on the input string. On completion, it will return a LinkedList with the task that was edited.
	- Sample input: <b>@!edit</b> 0 <b>@!name</b> Buy Cupcakes <b>@!description</b> Henry's birthday (and he prefers cupcakes...)!!!
- "View" operation, it will return a LinkedList with all the tasks available.
	- Sample input: <b>@!view</b>
- "Delete" operation, it will delete the task based on the input string. On completion, it will return a LinkedList with the task that was edited.
	- Sample input: <b>@!delete</b> 1
- "Save" operation, it will store all the tasks to an external file. Completion, it will return a LinkedList of all the available tasks.
	- Sample input: <b>@!delete</b> 1


<b>Operations</b>
This class contains all the operations constant.

To get a list of keywords of a certain operations,

	Operations operations = new Operations();
	operations.addOpertaions; //Get a list of available add operations
	
To get the operation's constant,

	Operations.ADD_OPERATION;