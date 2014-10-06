Logic Component
=====

The logic component offers these features

<b>LogicMain.processInput(String input)</b>

To use it, simply type:

	LogicMain.processInput(someStringText)

When it detected...
- "ADD" operation, it will create a task based on the input string. On completion, it will return a LinkedList with the task that was added.
	- Sample input: <b>@!add</b> Buy Cake <b>@!description</b> It is Henry's birthday!!!
- "EDIT" operation, it will edit the task based on the input string. On completion, it will return a LinkedList with the task that was edited.
	- Sample input: <b>@!edit</b> 0 <b>@!name</b> Buy Cupcakes <b>@!description</b> Henry's birthday (and he prefers cupcakes...)!!!
- "View" operation, it will return a LinkedList with all the tasks available.
	- Sample input: <b>@!view</b>
- "Delete" operation, it will delete the task based on the input string. On completion, it will return a LinkedList with the task that was edited.
	- Sample input: <b>@!delete</b> 1


<b>OperationsConstant</b>
This class contains all the operations constant. 

Example:
For add operations, LogicMain.processInput(String) can understand   @!add   @!+  @!insert