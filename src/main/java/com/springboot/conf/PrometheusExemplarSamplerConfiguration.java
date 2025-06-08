package com.springboot.conf;

// Exemplar : 지표 데이터에서 특정 지점에 대한 추가적인 추가적인 컨텍스트 정보를 제공.
// 이를 통해 트레이스나 로그와 같은 데이터에 연결.
import io.prometheus.client.exemplars.tracer.otel_agent.OpenTelemetryAgentSpanContextSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 이 클래스는 OpenTelemetry와 Prometheus 사이에서 트레이스 데이터를 연동하는 역할.
// Exemplar 지표를 설정하는 데 필요.
@Configuration
public class PrometheusExemplarSamplerConfiguration {
    @Bean
    public OpenTelemetryAgentSpanContextSupplier openTelemetryAgentSpanContextSupplier() {
        return new OpenTelemetryAgentSpanContextSupplier();
    }
}
