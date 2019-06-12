const {toolbox} = require("../js/toolbox.js"); 

describe("Tests:", function () {
    
    jasmine.clock().install();

    const tools = toolbox();

    beforeEach(function () {
        let s = spyOn(console, 'log').and.callThrough();
        let elem = document.createElement('div');
        elem.id = "mycontainer";
        elem.innerHTML = `
        

        <form method="POST" id="form1" class="cta">
                            
                 <input class="name" type="text" name="username" value="kurka" />
                 <input required type="text" name="yob"  placeholder="yob" />
                 <input required type="submit" class="fit" value='Dodaj ogłoszenie' name="edit" /></li>

        </form>
        

        `;
        document.body.appendChild(elem)
    });

    afterEach(function () {
        $('#mycontainer').remove();
    });

    it("returned value should be censored", function () {
        expect(tools.censor('kurka')).toEqual('xxxxx');
    });

    it("returned value should be lowercase", function () {
        expect(tools.lowercase('VoLvO')).toEqual('volvo');
    });

    it("returned value should be wrong", function () {
        expect(tools.checklenght('788364254')).toEqual('wrong number');
    });

    it("username should be wrong because censored", function () {

        FormObject = document.forms['form1'];
        username = FormObject.elements["username"];
        username.value = 'kurka';

        username = tools.validate_username(username);

        expect(username.value).toEqual('Wrong username');
        expect(username.class).toEqual('Invalid');

        expect(console.log).not.toHaveBeenCalled();

    });

    it("username should be wrong because too long", function () {

        FormObject = document.forms['form1'];
        username = FormObject.elements["username"];
        username.value = 'kurkaaasdasa';

        username.value = tools.validate_username(username.value);

        expect(username.value).toEqual('Wrong username');
        expect(console.log).not.toHaveBeenCalled();

    });

    it("username should be ok", function () {

        FormObject = document.forms['form1'];
        username = FormObject.elements["username"];
        username.value = 'kaczka';

        tools.validate_username(username);

        expect(username.value).toEqual('kaczka');
        expect(console.log).not.toHaveBeenCalled();

    });

    it("username should be transformed to lowercase", function () {

        FormObject = document.forms['form1'];
        username = FormObject.elements["username"];
        username.value = 'BaRteK';

        username.value = tools.lowercase(username.value);


        expect(username.value).toEqual('bartek');
        expect(console.log).not.toHaveBeenCalled();

    });

    it("year should be wrong number", function () {

        FormObject = document.forms['form1'];
        yob = FormObject.elements["yob"];
        yob.value = '19966';

        yob.value = tools.checklenght(yob.value);

        expect(yob.value).toEqual('wrong number');
        expect(console.log).not.toHaveBeenCalled();

    });

    it("year should be ok", function () {

        FormObject = document.forms['form1'];
        yob = FormObject.elements["yob"];
        yob.value = '1996';


        expect(tools.checklenght(yob.value)).toEqual('1996');
        expect(console.log).not.toHaveBeenCalled();

    });

});

