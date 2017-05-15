
var app = function() {

    var self = {};
    self.is_configured = false;

    Vue.config.silent = false; // show all warnings

    // Extends an array
    self.extend = function(a, b) {
        for (var i = 0; i < b.length; i++) {
            a.push(b[i]);
        }
    };

    // Enumerates an array.
    var enumerate = function(v) {
        var k=0;
        v.map(function(e) {e._idx = k++;});
    };

    // Initializes an attribute of an array of objects.
    var set_array_attribute = function (v, attr, x) {
        v.map(function (e) {e[attr] = x;});
    };

    self.initialize = function () {
        document.addEventListener('deviceready', self.ondeviceready, false);
    };

    self.ondeviceready = function () {
        // This callback is called once Cordova has finished
        // its own initialization.
        console.log("The device is ready");
        $("#vue-div").show(); // This is jQuery.
        self.is_configured = true;
    };

    self.reset = function () {
	     self.vue.board= [
			{1: 'red'},
			{2: 'white'},
			{3: 'red'},
			{4: 'white'},
			{5: 'white'},
			{6: 'red'},
			{7: 'white'},
			{8: 'red'},
			{9: 'red'},
			{10: 'white'},
			{11: 'red'},
			{12: 'white'},
			{13: 'white'},
			{14: 'red'},
			{15: 'white'},
			{0: 'black'},
		   ];

    };

    self.shuffle = function(i, j) {
        // You need to implement this.
       if (self.vue.cellColor(i, j+1) == 'black'){
            self.vue.swap(4*i+j, 4*i+(j+1));
        } else if (self.cellColor(i, j-1) == 'black'){
            self.vue.swap(4*i+j, 4*i+(j-1));
        } else if (self.cellColor(i+1, j) == 'black'){
            self.vue.swap(4*i+j, 4*(i+1)+j)
        } else if (self.cellColor(i-1, j) == 'black'){
            self.vue.swap(4*i+j, 4*(i-1)+j);
        } else {
            console.log("You can't move!");
        }
	        console.log("Shuffle:" + i + ", " + j);
    };
	
     // Swap the cells
   
    self.scramble = function() {
        // Read the Wikipedia article.  If you just randomize,
        // the resulting puzzle may not be solvable.
    };

      // Swap the cells
    self.swap = function(current, target) {
        temp = self.vue.board[current];
        Vue.set(self.vue.board, current, self.vue.board[target]);
        Vue.set(self.vue.board, target, temp);
    }

    // Returns the (String) color associated with that cell
    self.cellColor = function(i, j) {
        try {
            return (Object.values(self.vue.board[4*i+j]));
        } catch (exception) {
            //console.error('NULL ERROR', exception.message);
        }

    };

    self.vue = new Vue({
        el: "#vue-div",
        delimiters: ['${', '}'],
        unsafeDelimiters: ['!{', '}'],
        data: {
            board: [
			{1: 'red'},
			{2: 'white'},
			{3: 'red'},
			{4: 'white'},
			{5: 'white'},
			{6: 'red'},
			{7: 'white'},
			{8: 'red'},
			{9: 'red'},
			{10: 'white'},
			{11: 'red'},
			{12: 'white'},
			{13: 'white'},
			{14: 'red'},
			{15: 'white'},
			{0: 'black'},
		   ]
        },
        methods: {
            reset: self.reset,
            shuffle: self.shuffle,
            scramble: self.scramble,
	    swap: self.swap,
	    cellColor: self.cellColor,
        }

    });

    self.reset();

    return self;
};

var APP = null;

// This will make everything accessible from the js console;
// for instance, self.x above would be accessible as APP.x
jQuery(function(){
    APP = app();
    APP.initialize();
});
