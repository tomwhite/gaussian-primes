/*
Copyright 2009 Tom White

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

var composites;

// Use the Sieve of Eratosthenes (http://en.wikipedia.org/wiki/Sieve_of_Eratosthenes)
// to find primes up to a given number.
// This implementation takes advantage of the fact that JavaScript arrays are sparse.
function sieve(limit) {
	composites = []; // global, mark composites as true
	composites[1] = true; // 1 is not prime
	for (var i = 1; i * i <= limit; i++) {
		if (composites[i]) { continue; }
		for (var j = 2; j <= limit / i; j++) {
			composites[i * j] = true;
		}
	}	
}

// Test whether an integer n is prime. Call sieve() first.
function prime(n) {
	return !composites[n];
}

// Test whether the complex number a + ib (where a and b are integers) is a Gaussian prime.
// The definition for a Gaussian prime can be found at http://mathworld.wolfram.com/GaussianPrime.html,
// and http://en.wikipedia.org/wiki/Gaussian_integer.
function gaussianPrime(a, b) {
	if (a === 0) {
		return prime(Math.abs(b)) && Math.abs(b) % 4 === 3;
	} else if (b === 0) {
		return prime(Math.abs(a)) && Math.abs(a) % 4 === 3;
	} else {
		return prime(a*a + b*b);
	}
}

// Find up to maxPoints Gaussian primes whose real or imaginary part is
// no more than a given size (max).
// This function returns an array of point objects, which are sorted by modulus.
// Only primes with a non-negative real part and a non-negative imaginary part that is less than or equal to the real part are returned
// (or, equivalently, those for which 0 <= theta <= pi/4>).
function sortedGaussianPrimes(max, maxPoints) {
	
	sieve(2*max*max+1);
	
	var points = [];
	var i = 0;
	for (var x = 0; x <= max; x++) {
		for (var y = 0; y <= x; y++) {
			if (gaussianPrime(x, y)) {
				points[i++] = {r2: x*x + y*y, x: x, y: y};
			}
		}
	}

	points.sort(function compare(a, b) {
		return a.r2 - b.r2;
	});
	
	if (points.length > maxPoints) {
		points.length = maxPoints;
	}
	return points;
}

// Plot a path between points (of Gaussian primes) of increasing modulus
// using straight lines or arcs.
function drawLines(ctx, points, color, curved) {
	var p = points;

	ctx.strokeStyle = color;
	ctx.lineWidth = 0.1;
	ctx.beginPath();
	ctx.moveTo(0, 0);

	ctx.moveTo(p[0].x, p[0].y);

	for (var i = 1; i < p.length; i++) {
		if (curved) {
			var previousTheta = Math.atan2(p[i-1].y, p[i-1].x);
			var r = Math.sqrt(p[i].r2);
			var theta = Math.atan2(p[i].y, p[i].x);
			ctx.lineTo(r * Math.cos(previousTheta), r * Math.sin(previousTheta));
			ctx.arc(0, 0, r, previousTheta, theta, previousTheta > theta);
		} else {
			ctx.lineTo(p[i].x, p[i].y);
		}
	}

	ctx.stroke();
	ctx.closePath();
}

// Plot the points (of Gaussian primes) of increasing modulus as circles.
function drawPoints(ctx, points, color) {
	var p = points;
	
	ctx.strokeStyle = color;
	ctx.fillStyle = color;

	ctx.beginPath();
	for (var i = 0; i < p.length; i++) {
		ctx.moveTo(p[i].x, p[i].y);
		ctx.arc(p[i].x, p[i].y, 0.1, 0, 2 * Math.PI, true);
		ctx.fill();
	}
	ctx.closePath();
	ctx.stroke();
}

// Plot the points (of Gaussian primes) in the plane as squares.
function drawPlot(ctx, max, color) {
	
	sieve(2*max*max+1);

	ctx.strokeStyle = color;
	ctx.fillStyle = color;
	
	for (var x = 0; x <= max; x++) {
		for (var y = 0; y <= x; y++) {
			if (gaussianPrime(x, y)) {
				ctx.fillRect(x, y, 1, 1);
				ctx.fillRect(y, x, 1, 1);
				ctx.fillRect(-x, y, 1, 1);
				ctx.fillRect(-y, x, 1, 1);
				ctx.fillRect(x, -y, 1, 1);
				ctx.fillRect(y, -x, 1, 1);
				ctx.fillRect(-x, -y, 1, 1);
				ctx.fillRect(-y, -x, 1, 1);
			}
		}
	}

}
