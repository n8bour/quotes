import http from 'k6/http';
import {check} from 'k6';
import {SharedArray} from 'k6/data';

const quotes = new SharedArray('data.json', function () {
    return JSON.parse(open('../src/main/docker/deployment/init-db/data.json'));
});

export const options = {
    // A number specifying the number of VUs to run concurrently.
    thresholds: {
        http_req_failed: ['rate<0.01'], // http errors should be less than 1%
        http_req_duration: ['p(99)<30000'], // 99% of requests should be below 30s
    },
    scenarios: {
        my_scenario1: {
            executor: 'constant-arrival-rate',
            duration: '3s', // total duration
            preAllocatedVUs: 3, // to allocate runtime resources     preAll

            rate: 1, // number of constant iterations given `timeUnit`
            timeUnit: '1s',
        },
    },
}

// See https://grafana.com/docs/k6/latest/examples/get-started-with-k6/ to learn more
// about authoring k6 scripts.
//
export default function () {
    const res = http.get('http://localhost:8080/quotes');

    check(res, {
        'status was 200': (r) => r.status === 200,
        'Length must be the same': (r) => r.json().length === quotes.length
    });
    console.log('Response time was ' + String(res.timings.duration) + ' ms');
}
