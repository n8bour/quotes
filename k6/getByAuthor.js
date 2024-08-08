import http from 'k6/http';
import {check} from 'k6';
import {SharedArray} from 'k6/data';

const quotes = new SharedArray('data.json', function () {
    return JSON.parse(open('../docker/deployment/init-db/data.json'));
});

export const options = {
    thresholds: {
        http_req_failed: ['rate<0.01'], // http errors should be less than 1%
        http_req_duration: ['p(99)<4'], // 99% of requests should be below 4ms
    },
    scenarios: {
        my_scenario1: {
            executor: 'constant-arrival-rate',
            duration: '10s', // total duration
            preAllocatedVUs: 50, // to allocate runtime resources     preAll

            rate: 50, // number of constant iterations given `timeUnit`
            timeUnit: '1s',
        },
    },
}
// See https://grafana.com/docs/k6/latest/examples/get-started-with-k6/ to learn more
// about authoring k6 scripts.
//
export default function () {
    const quote = quotes[Math.floor(Math.random() * quotes.length)];
    const res = http.get('http://localhost:8080/quotes/authors/' + quote.quoteAuthor);

    check(res, {
        'status was 200': (r) => r.status === 200,
        'Check if the first element is the Author we fetched': (r) => r.json()[0].author === quote.quoteAuthor
    });
    //console.log('Response time was ' + String(res.timings.duration) + ' ms');
}
