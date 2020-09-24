// ts -> .d.ts 翻译文件 -> js   ts怎么引入js @type/xxx
import superagent from 'superagent';
import cheerio from 'cheerio'

interface Course {
    title: string;
    count: number;
}

interface Result {
    date: number;
    data: Course[];
}

class Crowller {
    private secret = 'secretKey';
    private url = `http://ss.com?secret=${this.secret}`;

    async getRawHtml() {
        const result = await superagent.get(this.url);
        return result.text;
    }

    getCourseInfo(html: string): Result {
        const courses: Course[] = [];
        const $ = cheerio.load(html);
        const courseItems = $('.course-item');
        courseItems.map((_index, element) => {
            const descs = $(element).find('.course-desc');
            const title = descs.eq(0).text();
            const count = parseInt(descs.eq(1).text().split(": ")[1]);
            courses.push({title, count})
        });
        return {
            date: new Date().getTime(),
            data: courses,
        };
    }

    async initSpiderProcess() {
        const html = await this.getRawHtml();
        const result = this.getCourseInfo(html);
        console.log(result);
    }

    constructor() {
        this.initSpiderProcess();
    }
    
}

const crowller = new Crowller();