package com.dhnhan.alarms

class AlarmTask extends TimerTask {
    String msg

    AlarmTask(String msg) {
        this.msg = msg
    }

    @Override
    void run() {
        def command = "msg console \"" + this.msg + "\""
        command.execute()
    }
}
