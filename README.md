Weather Observations Application

Application uses for mine the logs of a weather balloon.

Docker uses only for running RabbitMQ.

To run application you must:
1. Start rabbit - run the command -  "docker-compose up -d"
2. Application start within a command - "gradle bootRun"

To produce some test data and put statistic into the log you may do GET request to localhost:8080/api/produce.
To get some statistic from the past event you may execute  GET request to
localhost:8080/api/statistics/history?dateFrom=2019-12-23T12:00&dateTo=2020-12-23T12:00.
