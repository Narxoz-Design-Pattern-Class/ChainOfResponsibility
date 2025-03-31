abstract class Logger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    protected int level;
    protected Logger nextLogger;

    public void setNextLogger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    protected abstract void write(String message);
}

class ConsoleLogger extends Logger {
    public ConsoleLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Console::Logger: " + message);
    }
}

class FileLogger extends Logger {
    public FileLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("File::Logger: " + message);
    }
}

class ErrorLogger extends Logger {
    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Error::Logger: " + message);
    }
}

// Setup Chain
Logger errorLogger = new ErrorLogger(Logger.ERROR);
Logger fileLogger = new FileLogger(Logger.DEBUG);
Logger consoleLogger = new ConsoleLogger(Logger.INFO);

errorLogger.setNextLogger(fileLogger);
fileLogger.setNextLogger(consoleLogger);

// Use the chain
errorLogger.logMessage(Logger.INFO, "This is an information.");
errorLogger.logMessage(Logger.DEBUG, "This is a debug level information.");
errorLogger.logMessage(Logger.ERROR, "This is an error information.");