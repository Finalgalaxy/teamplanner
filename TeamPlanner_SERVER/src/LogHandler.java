import java.io.*;
import java.util.logging.*;
import javax.swing.*;

public class LogHandler extends Handler {

	private JTextArea textArea;

	public LogHandler(JTextArea textArea){
		this.textArea=textArea;
	}

	@Override
	public void publish(final LogRecord record) {
		synchronized (textArea) {
			StringWriter text = new StringWriter();
			PrintWriter out = new PrintWriter(text);
			out.printf("[%s] [Thread-%d]: %s.%s -> %s\n", record.getLevel(),
					record.getThreadID(), record.getSourceClassName(),
					record.getSourceMethodName(), record.getMessage());
			append(text.toString());

			//textArea.setCaretPosition(textArea.getDocument().getLength());
		}


	}

	/////////////////////////////////////////////////////////////////

	private void append(String s) {
		//textArea.setText(textArea.getText() + s);
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				textArea.append(s);
				textArea.repaint();
				textArea.updateUI();
				textArea.paint(textArea.getGraphics());
				textArea.update(textArea.getGraphics());

				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
		});

	}

	/////////////////////////////////////////////////////////////////

	public JTextArea getTextArea() {
		return this.textArea;
	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	//...
}