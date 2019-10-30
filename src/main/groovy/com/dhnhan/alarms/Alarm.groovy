package com.dhnhan.alarms

import com.dhnhan.alarms.exception.AlarmException

class Alarm {
    Date date
    String message

    Alarm(String line) throws AlarmException {
        if (line.startsWith('#')) {
            throw new AlarmException(Const.Error.IGNORE)
        }
        String regex = ']='
        int indexTimeEnd = line.indexOf(regex);
        if (indexTimeEnd < 0) {
            throw new AlarmException(Const.Error.FORMAT)
        }
        String time = line.substring(1, indexTimeEnd);
        if (time == null || time.isEmpty()) {
            throw new AlarmException(Const.Error.FORMAT)
        }
        String[] times = time.split(':')
        if (times.length != 2) {
            throw new AlarmException(Const.Error.FORMAT)
        }
        try {
            int hour = Integer.parseInt(times[0])
            int minute = Integer.parseInt(times[1])
            Calendar cal = Calendar.getInstance()
            if (cal.get(Calendar.HOUR_OF_DAY) >= hour) {
                if (cal.get(Calendar.HOUR_OF_DAY) == hour) {
                    if (cal.get(Calendar.MINUTE) > minute) {
                        cal.add(Calendar.DATE, 1)
                    }
                } else {
                    cal.add(Calendar.DATE, 1)
                }
            }
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            this.date = cal.getTime()
            this.message = line.substring(indexTimeEnd + regex.length(), line.length())
        } catch (NumberFormatException e) {
            throw new AlarmException(Const.Error.FORMAT)
        }
    }
}
