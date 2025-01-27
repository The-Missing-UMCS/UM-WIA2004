#[derive(Clone)]
pub struct Process {
    pub process_id: String,
    pub arrival_time: f64,
    pub burst_time: f64,
    pub turnaround_time: f64,
    pub waiting_time: f64,
}

impl Process {
    pub fn new(process_id: &str, arrival_time: f64, burst_time: f64) -> Self {
        Self {
            process_id: process_id.to_string(),
            arrival_time,
            burst_time,
            turnaround_time: 0.0,
            waiting_time: 0.0,
        }
    }

    pub fn update_times(&mut self, start_time: f64) {
        self.waiting_time = (start_time - self.arrival_time).max(0.0);
        self.turnaround_time = self.waiting_time + self.burst_time;
    }
}
