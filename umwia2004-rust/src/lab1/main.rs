mod process;
mod scheduler;

use process::Process;
use scheduler::fcfs::FCFS;

// #[derive(Clone)]
// struct Process {
//     process_id: String,
//     arrival_time: f64,
//     burst_time: f64,
//     turnaround_time: f64,
//     waiting_time: f64,
// }
//
// impl Process {
//     fn new(process_id: &str, arrival_time: f64, burst_time: f64) -> Self {
//         Self {
//             process_id: process_id.to_string(),
//             arrival_time,
//             burst_time,
//             turnaround_time: 0.0,
//             waiting_time: 0.0,
//         }
//     }
//
//     fn update_times(&mut self, start_time: f64) {
//         self.waiting_time = (start_time - self.arrival_time).max(0.0);
//         self.turnaround_time = self.waiting_time + self.burst_time;
//     }
// }
//
// trait Scheduler {
//     fn execute(&mut self);
//     fn show(&self);
// }
//
// struct FCFS {
//     processes: Vec<Process>,
//     current_time: f64,
//     completed_processes: Vec<Process>,
// }
//
// impl FCFS {
//     fn new(processes: Vec<Process>) -> Self {
//         Self {
//             processes,
//             current_time: 0.0,
//             completed_processes: Vec::new(),
//         }
//     }
//
//     fn update(&mut self, process: &mut Process) {
//         process.update_times(self.current_time);
//         self.current_time += process.burst_time;
//         self.completed_processes.push(process.clone());
//     }
// }
//
// impl Scheduler for FCFS {
//     fn execute(&mut self) {
//         // Sort processes by arrival time
//         self.processes.sort_by(|a, b| a.arrival_time.partial_cmp(&b.arrival_time).unwrap());
//
//         // Move processes out to avoid mutable borrowing conflicts
//         let mut temp_processes = std::mem::take(&mut self.processes);
//
//         for process in &mut temp_processes {
//             if self.current_time < process.arrival_time {
//                 self.current_time = process.arrival_time;
//             }
//             self.update(process);
//         }
//
//         // Return processes back to self after processing (optional if unused later)
//         self.processes = temp_processes;
//     }
//
//     fn show(&self) {
//         println!(
//             "{:>12} {:>15} {:>13} {:>18} {:>15}",
//             "Process ID", "Arrival Time", "Burst Time", "Turnaround Time", "Waiting Time"
//         );
//         for process in &self.completed_processes {
//             println!(
//                 "{:>12} {:>15.2} {:>13.2} {:>18.2} {:>15.2}",
//                 process.process_id,
//                 process.arrival_time,
//                 process.burst_time,
//                 process.turnaround_time,
//                 process.waiting_time
//             );
//         }
//     }
// }

fn main() {
    let processes = vec![
        Process::new("P1", 0.0, 7.0),
        Process::new("P2", 0.0, 4.0),
        Process::new("P3", 0.0, 1.0),
        Process::new("P4", 0.0, 4.0),
    ];

    println!("FCFS Scheduling:");
    let mut fcfs = FCFS::new(processes);
    fcfs.execute();
    fcfs.show();
}
