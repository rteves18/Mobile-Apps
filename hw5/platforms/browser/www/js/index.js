
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
       if (self.vue.getKey(i, j+1) == '0'){
            self.vue.switchCell(4*i+j, 4*i+(j+1));
        } else if (self.getKey(i, j-1) == '0'){
            self.vue.switchCell(4*i+j, 4*i+(j-1));
        } else if (self.getKey(i+1, j) == '0'){
            self.vue.switchCell(4*i+j, 4*(i+1)+j)
        } else if (self.getKey(i-1, j) == '0'){
            self.vue.switchCell(4*i+j, 4*(i-1)+j);
        } else {
            console.log("Can't move from there");
        }
    };
    self.scrambleExe = function() {
        self.vue.ScrambledBoard = [];
        // Shuffle by Fisher-Yates
        for (count = self.vue.board.length - 1; count >= 0; count--) {
            var j = Math.floor(Math.random() * (count + 1));
            self.vue.switchCell(count, j);

            // Add scramble array
            self.vue.ScrambledBoard.push(Object.keys(self.vue.board[count])[0]);
        }
        self.vue.ScrambledBoard.reverse();
        console.log("Inversion Count: " + 
        self.getInversion(self.vue.ScrambledBoard));
        console.log("Empty cell is " + self.locateBlackTile(self.vue.ScrambledBoard));
    }
	
    // Prints elements in array for testing purposes
    self.printArray = function(array) {
        for (i = 0; i < array.length; i++) {
            console.log(array[i]);
        }
    }


 self.scramble = function() {
	//execute scramble
        self.scrambleExe();
	//continue to scramble if not scramblable
        while ( self.isSolvable(self.vue.ScrambledBoard) == false ) {
            self.scrambleExe();
        }
    };
    // Determines if scrambled board is solvable
    self.isSolvable = function(boardArray) {
        // Get inversion count
        var isInvCntEven = self.isEven(self.getInversion(boardArray));

        // Get the position of the blank cell
        var isEmptyCellEvenRow = self.locateBlackTile(boardArray);

        // Board is solvable if:
        // - blank cell is on an even row counting from bottom && num inversion is odd
        // OR - blank cell is on an odd row counting from bottom && num inversion is even
        if ( (isInvCntEven && !isEmptyCellEvenRow) || 
             (!isInvCntEven && isEmptyCellEvenRow) ) {
            return true;
            console.log("scramble is solvable");
        } else {
            return false;
            console.log("scramble is unsolvable");
        }
    }

    // Count inversions in board array
    self.getInversion = function(boardArray) {
        inc = 0;
        for (var i = 0; i < boardArray.length - 1; i++) {
            for (var j = i + 1; j < boardArray.length; j++) {
                if (boardArray[j] && boardArray[i] && boardArray[i] > boardArray[j])
                    inc++;
            }
        }
        return inc;
    }

    //checks if even or not
    self.isEven = function (number) {
        if (number % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Finds position of blank cell
    self.locateBlackTile = function(boardArray) {
        var index = 0;

        //Located black 0 tile 
        for (i = 0; i < boardArray.length; i++) {
            if (boardArray[i] == 0) {
                index = i;
            }
        }
        if ( (index > -1 && index < 4) || 
             (index > 7 && index < 12) ) {
            return true;
        } else {
            return false;
        }
    }

    //switch cell for tiles to move 
    self.switchCell = function(current, target) {
        temp = self.vue.board[current];
        Vue.set(self.vue.board, current, self.vue.board[target]);
        Vue.set(self.vue.board, target, temp);
    }

    // Returns cell key 
    self.getKey = function(i, j) {
        try {
            return (Object.keys(self.vue.board[4*i+j]));
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
	    switchCell: self.switchCell,
	    getKey: self.getKey,
	    printArray: self.printArray,
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
