package com.dhnhan

import com.dhnhan.alarms.Alarms

class AppMain {

    static void main(String[] args) {
        new Thread(new Alarms()).start()
    }
}
