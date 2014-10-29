import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class Autocomplete implements DocumentListener {

	private static enum Mode {
		INSERT,
		COMPLETION
	};

	private JTextField textField;
	private final List<String> keywords;
	private Mode mode = Mode.INSERT;
	private String addHelp = "Suggestions:\n\n;add\n\n;+\n\n;insert";
	private String deleteHelp = "Suggestions:\n\n;delete\n\n;-\n\n;remove\n\n;rm";
	private String viewHelp = "Suggestions:\n\n;view\n\n;display\n\n;\n\n;v";
	private String descriptionHelp = "Suggestions:\n\n;description\n\n;i\n\n;information\n\n;info";
	private String editHelp = "Suggestions:\n\n;edit\n\n;update\n\n;~";
	private String undoHelp = "Suggestions:\n\n;undo\n\n;back";
	private String findHelp = "Suggestions:\n\n;find\n\n;search\n\n;look";
	private String labelHelp = "Suggestions:\n\n;label\n\n;lb";
	private String remindHelp = "Suggestions:\n\n;remind\n\n;reminder\n\n;notify";
	private String deadlineHelp = "Suggestions:\n\n;by\n\n;on\n\n;date\n\n;when";

	public Autocomplete(JTextField textField, List<String> keywords) {
		this.textField = textField;
		this.keywords = keywords;
		Collections.sort(keywords);
	}

	@Override
	public void changedUpdate(DocumentEvent ev) { }

	@Override
	public void removeUpdate(DocumentEvent ev) { }

	@Override
	public void insertUpdate(DocumentEvent ev) {
		if (ev.getLength() != 1)
			return;

		int pos = ev.getOffset();
		String content = null;
		try {
			content = textField.getText(0, pos + 1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// Find where the word starts
		int w;
		for (w = pos; w >= 0; w--) {
			if (!Character.isLetter((content.charAt(w)))) {
				break;
			}
		}

		// Too few chars
		if (pos - w < 1)
			return;

		String prefix = content.substring(w + 1).toLowerCase();
		int n = Collections.binarySearch(keywords, prefix);
		if (n < 0 && -n <= keywords.size()) {
			String match = keywords.get(-n - 1);
			if (match.startsWith(prefix)) {

				String completion = match.substring(pos - w);
				// We cannot modify Document from within notification,
				// so we submit a task that does the change later
				suggest(match);
				SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
			}
		} else {
			// Nothing found
			mode = Mode.INSERT;
		}
	}

	private void suggest(String match) {
		switch (match) {
		case "add":
			GuiMain.feedback.setText(addHelp);
			break;
		case "delete":
			GuiMain.feedback.setText(deleteHelp);
			break;
		case "view":
			GuiMain.feedback.setText(viewHelp);
			break;
		case "description":
			GuiMain.feedback.setText(descriptionHelp);
			break;
		case "edit":
			GuiMain.feedback.setText(editHelp);
			break;
		case "undo":
			GuiMain.feedback.setText(undoHelp);
			break;
		case "by":
			GuiMain.feedback.setText(deadlineHelp);
			break;
		case "label":
			GuiMain.feedback.setText(labelHelp);
			break;
		case "find":
			GuiMain.feedback.setText(findHelp);
			break;
		case "remind":
			GuiMain.feedback.setText(remindHelp);
			break;
		}
	}

	public class CommitAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5794543109646743416L;

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (mode == Mode.COMPLETION) {
				int pos = textField.getSelectionEnd();
				StringBuffer sb = new StringBuffer(textField.getText());
				sb.insert(pos, " ");
				textField.setText(sb.toString());
				textField.setCaretPosition(pos + 1);
				mode = Mode.INSERT;
			} else {
				textField.replaceSelection("\t");
			}
		}
	}

	private class CompletionTask implements Runnable {
		private String completion;
		private int position;

		CompletionTask(String completion, int position) {
			this.completion = completion;
			this.position = position;
		}

		public void run() {
			StringBuffer sb = new StringBuffer(textField.getText());
			sb.insert(position, completion);
			textField.setText(sb.toString());
			textField.setCaretPosition(position + completion.length());
			textField.moveCaretPosition(position);
			mode = Mode.COMPLETION;
		}
	}

}