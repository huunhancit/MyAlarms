package com.dhnhan.alarms

import com.dhnhan.alarms.exception.AlarmException

import java.util.concurrent.TimeUnit

class Alarms implements Runnable {
    private Properties props
    private long lastModified
    def tasks = [] as Timer[]

    Alarms() {
        this.loadProperties()
    }

    private void loadProperties() {
        this.props = new Properties()
        this.props.load(Alarms.class.getClassLoader().getResourceAsStream("app.properties"))
    }

    @Override
    void run() {
        while (true) {
            File file = new File(this.props.getProperty(Const.Props.FILE_CONFIG))
            if (file.lastModified() > this.lastModified) {
                tasks.each {
                    it.cancel()
                }
                tasks.clear()
                file.eachLine {
                    try {
                        Alarm alarm = new Alarm(it)
                        Timer timer = new Timer()
                        timer.schedule(new AlarmTask(alarm.getMessage()), alarm.getDate(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS))
                        tasks += timer
                        println "Schedule at date: ${alarm.getDate()}"
                    } catch (AlarmException e) {
                        if (Const.Error.IGNORE == e.getMessage()) {
                            //ignore it
                            println("Ignore it.....")
                        } else {
                            println("error parser")
                        }
                    }
                }
                this.lastModified = file.lastModified()
            }
            Thread.sleep(Const.TIME_SLEEP)
        }
    }
}
