package com.xumpy.timesheets.services.session;

import com.xumpy.thuisadmin.dao.sqlite.model.TimeRecording;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class SessionTimesheet {
    private List<TimeRecording> timesheetTable;

    public List<TimeRecording> getTimesheetTable() {
        return timesheetTable;
    }

    public void setTimesheetTable(List<TimeRecording> timesheetTable) {
        this.timesheetTable = timesheetTable;
    }
}
