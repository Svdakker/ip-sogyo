# Project Architecture

```mermaid
flowchart TD
  Client <--> Api{Api}
  Domain <--> Api
  Persistence <--> Api
  Persistence <--> DB[(Database)]
```
