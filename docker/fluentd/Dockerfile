FROM fluent/fluentd:v1.12.0-debian-1.0
USER root
RUN ["gem", "install", "fluent-plugin-elasticsearch", "--version", "5.3.0"]
RUN ["gem", "install", "fluent-plugin-input-gelf", "--version", "0.3.2"]
COPY ./config/fluent.conf /fluentd/etc/fluent.conf