use crate::process::Process;

pub struct FCFS {
    processes: Vec<Process>,
    current_time: f64,
    completed_processes: Vec<Process>,
}

impl FCFS {
    pub fn new(processes: Vec<Process>) -> Self {
        Self {
            processes,
            current_time: 0.0,
            completed_processes: Vec::new(),
        }
    }

    fn update(&mut self, process: &mut Process) {
        process.update_times(self.current_time);
        self.current_time += process.burst_time;
        self.completed_processes.push(process.clone());
    }

    pub fn execute(&mut self) {
        // Sort processes by arrival time
        self.processes
            .sort_by(|a, b| a.arrival_time.partial_cmp(&b.arrival_time).unwrap());

        // Move processes out to avoid borrowing conflicts
        let mut temp_processes = std::mem::take(&mut self.processes);

        for process in &mut temp_processes {
            if self.current_time < process.arrival_time {
                self.current_time = process.arrival_time;
            }
            self.update(process);
        }

        // Return processes back (optional)
        self.processes = temp_processes;
    }

    pub fn show(&self) {
        println!(
            "{:>12} {:>15} {:>13} {:>18} {:>15}",
            "Process ID", "Arrival Time", "Burst Time", "Turnaround Time", "Waiting Time"
        );
        for process in &self.completed_processes {
            println!(
                "{:>12} {:>15.2} {:>13.2} {:>18.2} {:>15.2}",
                process.process_id,
                process.arrival_time,
                process.burst_time,
                process.turnaround_time,
                process.waiting_time
            );
        }
    }
}
